package alb.project.system.service;

import java.util.List;

import alb.project.system.domain.SysPost;
import alb.project.system.domain.SysUserPost;

/**
 * Post information The service layer
 *
 */
public interface ISysPostService
{
    /**
     * Query the job information set
     * 
     * @param post Post information
     * @return Post a list of
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
     * Calibration post Name
     * 
     * @param post Post information
     * @return The results of
     */
    String checkPostNameUnique(SysPost post);

    /**
     * Check post code
     * 
     * @param post Post information
     * @return The results of
     */
    String checkPostCodeUnique(SysPost post);

    /**
     * Through the postIDQuery the number of positions used
     * 
     * @param postId jobsID
     * @return The results of
     */
    int countUserPostById(Long postId);

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
     * @throws Exception abnormal
     */
    int deletePostByIds(Long[] postIds);

    /**
     * Added Saving job information
     * 
     * @param post Post information
     * @return The results of
     */
    int insertPost(SysPost post);

    /**
     * Modify save job information
     * 
     * @param post Post information
     * @return The results of
     */
    int updatePost(SysPost post);

    List<String> selectPostNameByUserId(Long userId);

    /**
     * Search for jobs by job code
     * @param ygs
     * @return
     */
    SysPost selectPostByPostCode(String ygs);

    /**
     * According to the useridAnd departmentsidQuery whether the record exists
     * @param userId
     * @param postId
     * @return
     */
    SysUserPost checkUserIsThisPost(Long userId, Long postId);

    /**
     * Obtain a random operatorid
     * @return
     */
    Long getRandomUserId(Long postId);
}
