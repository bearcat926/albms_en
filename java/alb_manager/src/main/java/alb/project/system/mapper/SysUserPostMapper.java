package alb.project.system.mapper;

import java.util.List;

import alb.project.system.domain.SysUserPost;
import org.apache.ibatis.annotations.Param;

/**
 * User and job association table The data layer
 *
 */
public interface SysUserPostMapper {
    /**
     * By the userIDDelete the association between a user and a job
     *
     * @param userId The userID
     * @return The results of
     */
    int deleteUserPostByUserId(Long userId);

    /**
     * Through the postIDQuery the number of positions used
     *
     * @param postId jobsID
     * @return The results of
     */
    int countUserPostById(Long postId);

    /**
     * Delete the association between a user and a job in batches
     *
     * @param ids Data to be deletedID
     * @return The results of
     */
    int deleteUserPost(Long[] ids);

    /**
     * Add user post information in batches
     *
     * @param userPostList User Role List
     * @return The results of
     */
    int batchUserPost(List<SysUserPost> userPostList);

    /**
     * According to the useridAnd departmentsidQuery whether the record exists
     *
     * @param userId
     * @param postId
     * @return
     */
    SysUserPost checkUserIsThisPost(@Param("userId") Long userId, @Param("postId") Long postId);

    /**
     * Obtain a random operatorid
     *
     * @param postId
     * @return
     */
    Long getRandomUserId(@Param("postId") Long postId);
}
