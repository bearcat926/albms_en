package alb.project.system.service.impl;

import java.util.List;

import alb.project.system.domain.SysNotice;
import alb.project.system.mapper.SysNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import alb.project.system.service.ISysNoticeService;

/**
 * The announcement Service layer implementation
 *
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService
{
    @Autowired
    private SysNoticeMapper noticeMapper;

    /**
     * Querying Bulletin Information
     * 
     * @param noticeId The announcementID
     * @return Announcement of the information
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId)
    {
        return noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * Querying the Bulletin List
     * 
     * @param notice Announcement of the information
     * @return The announcement set
     */
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice)
    {
        return noticeMapper.selectNoticeList(notice);
    }

    /**
     * The new announcement
     * 
     * @param notice Announcement of the information
     * @return The results of
     */
    @Override
    public int insertNotice(SysNotice notice)
    {
        return noticeMapper.insertNotice(notice);
    }

    /**
     * Modify bulletin
     * 
     * @param notice Announcement of the information
     * @return The results of
     */
    @Override
    public int updateNotice(SysNotice notice)
    {
        return noticeMapper.updateNotice(notice);
    }

    /**
     * Deleting a Bulletin Object
     * 
     * @param noticeId The announcementID
     * @return The results of
     */
    @Override
    public int deleteNoticeById(Long noticeId)
    {
        return noticeMapper.deleteNoticeById(noticeId);
    }

    /**
     * Delete bulletins in batches
     * 
     * @param noticeIds Bulletin to deleteID
     * @return The results of
     */
    @Override
    public int deleteNoticeByIds(Long[] noticeIds)
    {
        return noticeMapper.deleteNoticeByIds(noticeIds);
    }
}
