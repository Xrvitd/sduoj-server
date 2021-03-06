/**
 * The GNU General Public License
 * Copyright (c) 2020-2020 zhangt2333@gmail.com
 **/

package cn.edu.sdu.qd.oj.submit.pojo;

import cn.edu.sdu.qd.oj.common.config.DateToTimestampSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName SubmissionJudgeBo
 * @Description TODO
 * @Author zhangt2333
 * @Date 2020/3/14 18:59
 * @Version V1.0
 **/
@Data
@Table(name = "oj_submissions")
public class SubmissionJudgeBo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "s_id")
    private Long submissionId;

    @Column(name = "p_id")
    private Integer problemId;

    @Column(name = "u_id")
    private Integer userId;

    @Column(name = "l_id")
    private Integer languageId;

    @Column(name = "s_create_time")
    @JsonSerialize(using = DateToTimestampSerializer.class)
    private Date createTime;

    @Column(name = "s_code")
    private String code;

    @Column(name = "s_code_length")
    private Integer codeLength;
}