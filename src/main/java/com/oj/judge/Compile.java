package com.oj.judge;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.alibaba.druid.sql.visitor.functions.If;
import com.mysql.cj.xdevapi.JsonArray;
import com.oj.common.exception.CompileError;
import com.oj.common.exception.SubmitError;
import com.oj.common.exception.SystemError;
import com.oj.judge.entity.LanguageConfig;
import com.oj.util.Constants;
import com.oj.util.JudgeUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: guojun
 * 2024-11-26 15:36
 */

public class Compile {

    public static String compile(LanguageConfig languageConfig,
                                 String code,
                                 String language,
                                 HashMap<String,String> extraFiles) throws SystemError, CompileError, SubmitError {
        if (languageConfig == null){
            throw new RuntimeException("Unsupported language" + language);
        }

        JSONArray result = SandboxRun.compile(languageConfig.getMaxCpuTime(),
                languageConfig.getMaxRealTime(),
                languageConfig.getMaxMemory(),
                1024*1024*1024L,
                languageConfig.getSrcName(),
                languageConfig.getExeName(),
                parseCompileCommand(languageConfig.getCompileCommand()),
                languageConfig.getCompileEnvs(),
                code,
                extraFiles,
                true,
                false,
                null);

        JSONObject compileResult = (JSONObject) result.get(0);
        if (compileResult.getInt("status").intValue() != Constants.Judge.STATUS_ACCEPTED.getStatus()){
            throw new CompileError("Compile Error.", ((JSONObject) compileResult.get("files")).getStr("stdout"),
                    ((JSONObject) compileResult.get("files")).getStr("stderr"));
        }

        String fileId = ((JSONObject) compileResult.get("fileIds")).getStr(languageConfig.getExeName());
        if (StringUtils.isEmpty(fileId)) {
            throw new SubmitError("Executable file not found.", ((JSONObject) compileResult.get("files")).getStr("stdout"),
                    ((JSONObject) compileResult.get("files")).getStr("stderr"));
        }
        return fileId;
    }

    private static List<String> parseCompileCommand(String compileCommand){
        return JudgeUtils.translateCommandline(compileCommand);
    }
}
