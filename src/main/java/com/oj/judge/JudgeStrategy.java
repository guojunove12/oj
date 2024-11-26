package com.oj.judge;

import cn.hutool.json.JSONObject;
import com.oj.common.exception.CompileError;
import com.oj.common.exception.SubmitError;
import com.oj.common.exception.SystemError;
import com.oj.entity.Judge;
import com.oj.entity.Problem;
import com.oj.judge.entity.LanguageConfig;
import com.oj.util.Constants;
import com.oj.util.JudgeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;

/**
 * @Author: guojun
 * 2024-11-26 16:45
 */

@Slf4j(topic = "oj")
@Component
public class JudgeStrategy {

    @Resource
    private LanguageConfigLoader languageConfigLoader;

    public HashMap<String,Object> judge(Problem problem, Judge judge) throws CompileError, SystemError, SubmitError {
        HashMap<String,Object> map = new HashMap<>();
        String userFileId = null;

        //对用户源代码进行编译 获取tmpfs中的fileId
        LanguageConfig languageConfig= languageConfigLoader.getLanguageConfigByName(judge.getLanguage());

        //有的语言可能不支持编译, 目前有js、php不支持编译
        if (languageConfig.getCompileCommand() != null) {
            userFileId = Compile.compile(languageConfig.getCompileCommand(),
                    judge.getCode(),
                    judge.getLanguage(),
                    JudgeUtils.getProblemExtraFileMap(problem,"user"));
        }
        // 测试数据文件所在文件夹
        String testCasesDir = Constants.JudgeDir.TEST_CASE_DIR.getContent() + File.separator + "problem_" +problem.getId();

        JSONObject testCaseInfo = problemTest
    }
}
