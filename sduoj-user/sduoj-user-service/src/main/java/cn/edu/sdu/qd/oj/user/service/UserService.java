/**
 * The GNU General Public License
 * Copyright (c) 2020-2020 zhangt2333@gmail.com
 **/

package cn.edu.sdu.qd.oj.user.service;

import cn.edu.sdu.qd.oj.common.enums.ExceptionEnum;
import cn.edu.sdu.qd.oj.common.exception.OJException;
import cn.edu.sdu.qd.oj.user.mapper.UserMapper;
import cn.edu.sdu.qd.oj.user.pojo.User;
import cn.edu.sdu.qd.oj.user.utils.CodecUtils;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author zhangt2333
 * @Date 2020/2/26 11:29
 * @Version V1.0
 **/

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryById(Integer id) {
        User user = this.userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new OJException(ExceptionEnum.USER_NOT_FOUND);
        }
        return user;
    }

    public User queryUser(String username, String password) {
        // 查询
        User record = new User();
        record.setUsername(username);
        User user = this.userMapper.selectOne(record);
        // 校验用户名
        if (user == null) {
            throw new OJException(ExceptionEnum.USER_NOT_FOUND);
        }
        // 临时用
        if (!user.getPassword().equals(CodecUtils.md5Hex(password, "slat_string"))) {
            throw new OJException(ExceptionEnum.PASSWORD_NOT_MATCHING);
        }
        // TODO：校验加盐密码，选择加密方式和盐
//        if (!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) {
//            throw new OJException(ExceptionEnum.PASSWORD_NOT_MATCHING);
//        }
        // 用户名密码都正确
        return user;
    }
}