package life.guan.community.dto;

import life.guan.community.model.User;

import lombok.Data;

//将user和question数据进行关联 用于传输到index页面展示问题李彪 头像
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
