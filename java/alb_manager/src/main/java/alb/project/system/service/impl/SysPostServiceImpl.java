package alb.project.system.service.impl;

import java.util.List;

import alb.common.constant.UserConstants;
import alb.common.exception.CustomException;
import alb.project.system.domain.SysPost;
import alb.project.system.domain.SysUserPost;
import alb.project.system.mapper.SysPostMapper;
import alb.project.system.mapper.SysUserPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import alb.common.utils.StringUtils;
import alb.project.system.service.ISysPostService;

/**
 * Post information Service layer processing
 *
 */
@Service
public class SysPostServiceImpl implements ISysPostService
{
    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    /**
     * Query the job information set
     * 
     * @param post Post information
     * @return Job information set
     */
    @Override
    public List<SysPost> selectPostList(SysPost post)
    {
        return postMapper.selectPostList(post);
    }

    /**
     * Query all posts
     * 
     * @return Post a list of
     */
    @Override
    public List<SysPost> selectPostAll()
    {
        return postMapper.selectPostAll();
    }

    /**
     * Through the postIDQuery job information
     * 
     * @param postId jobsID
     * @return Role Object Information
     */
    @Override
    public SysPost selectPostById(Long postId)
    {
        return postMapper.selectPostById(postId);
    }

    /**
     * According to the userIDGet the job selection box list
     * 
     * @param userId The userID
     * @return Select the jobsIDThe list of
     */
    @Override
    public List<Integer> selectPostListByUserId(Long userId)
    {
        return postMapper.selectPostListByUserId(userId);
    }

    /**
     * Verify that the post name is unique
     * 
     * @param post Post information
     * @return The results of
     */
    @Override
    public String checkPostNameUnique(SysPost post)
    {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = postMapper.checkPostNameUnique(post.getPostName());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Verify that the post code is unique
     * 
     * @param post Post information
     * @return The results of
     */
    @Override
    public String checkPostCodeUnique(SysPost post)
    {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = postMapper.checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Through the postIDQuery the number of positions used
     * 
     * @param postId jobsID
     * @return The results of
     */
    @Override
    public int countUserPostById(Long postId)
    {
        return userPostMapper.countUserPostById(postId);
    }

    /**
     * Delete job information
     * 
     * @param postId jobsID
     * @return The results of
     */
    @Override
    public int deletePostById(Long postId)
    {
        return postMapper.deletePostById(postId);
    }

    /**
     * Delete job information in batches
     * 
     * @param postIds Jobs to be deletedID
     * @return The results of
     * @throws Exception abnormal
     */
    @Override
    public int deletePostByIds(Long[] postIds)
    {
        for (Long postId : postIds)
        {
            SysPost post = selectPostById(postId);
            if (countUserPostById(postId) > 0)
            {
                throw new CustomException(String.format("%1$sallocated,Can't delete", post.getPostName()));
            }
        }
        return postMapper.deletePostByIds(postIds);
    }

    /**
     * Added Saving job information
     * 
     * @param post Post information
     * @return The results of
     */
    @Override
    public int insertPost(SysPost post)
    {
        return postMapper.insertPost(post);
    }

    /**
     * Modify save job information
     * 
     * @param post Post information
     * @return The results of
     */
    @Override
    public int updatePost(SysPost post)
    {
        return postMapper.updatePost(post);
    }

    /**
     * According to the useridGet the job set
     * @param userId
     * @return
     */
    @Override
    public List<String> selectPostNameByUserId(Long userId) {
        return postMapper.selectPostNameByUserId(userId);
    }

    /**
     * Search for jobs by job code
     * @param ygs
     * @return
     */
    @Override
    public SysPost selectPostByPostCode(String ygs) {
        return postMapper.selectPostByPostCode(ygs);
    }

    /**
     * According to the useridAnd departmentsidQuery whether the record exists
     * @param userId
     * @param postId
     * @return
     */
    @Override
    public SysUserPost checkUserIsThisPost(Long userId, Long postId) {
        return userPostMapper.checkUserIsThisPost(userId,postId);
    }

    /**
     * Obtain a random operatorid
     * @param postId
     * @return
     */
    @Override
    public Long getRandomUserId(Long postId) {
        return userPostMapper.getRandomUserId(postId);
    }
}
