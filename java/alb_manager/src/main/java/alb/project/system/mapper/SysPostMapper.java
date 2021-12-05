package alb.project.system.mapper;

import java.util.List;

import alb.project.system.domain.SysPost;
import org.apache.ibatis.annotations.Param;

/**
 * Post information The data layer
 *
 */
public interface SysPostMapper {
    /**
     * Query the job data set
     *
     * @param post Post information
     * @return Job data set
     */
    List<SysPost> selectPostList(SysPost post);

    /**
     * Query all posts
     *
     * @return Post a list of
     */
    List<SysPost> selectPostAll();

    /**
     * Through the postIDQuery job information
     *
     * @param postId jobsID
     * @return Role Object Information
     */
    SysPost selectPostById(Long postId);

    /**
     * According to the userIDGet the job selection box list
     *
     * @param userId The userID
     * @return Select the jobsIDThe list of
     */
    List<Integer> selectPostListByUserId(Long userId);

    /**
     * Example Query the job group to which a user belongs
     *
     * @param userName The user name
     * @return The results of
     */
    List<SysPost> selectPostsByUserName(String userName);

    /**
     * Delete job information
     *
     * @param postId jobsID
     * @return The results of
     */
    int deletePostById(Long postId);

    /**
     * Delete job information in batches
     *
     * @param postIds Jobs to be deletedID
     * @return The results of
     */
    int deletePostByIds(Long[] postIds);

    /**
     * Modify job information
     *
     * @param post Post information
     * @return The results of
     */
    int updatePost(SysPost post);

    /**
     * New Post Information
     *
     * @param post Post information
     * @return The results of
     */
    int insertPost(SysPost post);

    /**
     * Calibration post Name
     *
     * @param postName Post the name
     * @return The results of
     */
    SysPost checkPostNameUnique(String postName);

    /**
     * Check post code
     *
     * @param postCode Post code
     * @return The results of
     */
    SysPost checkPostCodeUnique(String postCode);

    /**
     * According to the useridQuerying user Positions
     *
     * @param userId
     * @return
     */
    List<String> selectPostNameByUserId(Long userId);

    /**
     * Search for jobs by job code
     *
     * @param ygs
     * @return
     */
    SysPost selectPostByPostCode(@Param("code") String ygs);
}
