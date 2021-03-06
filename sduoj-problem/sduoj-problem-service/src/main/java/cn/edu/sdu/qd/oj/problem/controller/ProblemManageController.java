/**
 * The GNU General Public License
 * Copyright (c) 2020-2020 zhangt2333@gmail.com
 **/

package cn.edu.sdu.qd.oj.problem.controller;

import cn.edu.sdu.qd.oj.auth.entity.UserInfo;
import cn.edu.sdu.qd.oj.auth.utils.JwtUtils;
import cn.edu.sdu.qd.oj.common.entity.ApiResponseBody;
import cn.edu.sdu.qd.oj.common.enums.ApiExceptionEnum;
import cn.edu.sdu.qd.oj.common.exception.ApiException;
import cn.edu.sdu.qd.oj.common.utils.CookieUtils;
import cn.edu.sdu.qd.oj.problem.config.JwtProperties;
import cn.edu.sdu.qd.oj.problem.pojo.ProblemManageBo;
import cn.edu.sdu.qd.oj.problem.service.ProblemManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName ProblemManageController
 * @Description TODO
 * @Author zhangt2333
 * @Date 2020/4/3 21:30
 * @Version V1.0
 **/

@Controller
@EnableConfigurationProperties(JwtProperties.class)
@RequestMapping("/manage/problem")
public class ProblemManageController {

    @Autowired
    private ProblemManageService problemManageService;

    @Autowired
    private JwtProperties jwtProperties;


    @PostMapping("/query")
    @ApiResponseBody
    public ProblemManageBo queryManageBoById(@RequestBody Map json) {
        return this.problemManageService.queryById((Integer) json.get("problemId"));
    }

    @PostMapping("/create")
    @ApiResponseBody
    public Integer createProblemManageBo(@RequestBody ProblemManageBo problem, HttpServletRequest request)
    {
        // TODO: 鉴权
        String token = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());
        UserInfo userInfo;
        try {
            userInfo = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
        } catch (Exception e) {
            throw new ApiException(ApiExceptionEnum.UNKNOWN_ERROR);
        }
        problem.setUserId(userInfo.getUserId());
        this.problemManageService.createProblem(problem);
        return problem.getProblemId();
    }
}