package alb.project.system.service;

import java.util.List;

import alb.project.system.domain.SysNotice;

/**
 * The announcement The service layer
 *
 */
public interface ISysNoticeService
{
    /**
     * Querying Bulletin Information
     * 
     * @param noticeId The announcementID
     * @return Announcement of the information
     */
    SysNotice selectNoticeById(Long noticeId);

    /**
     * Querying the Bulletin List
     * 
     * @param notice Announcement of the information
     * @return The announcement set
     */
    List<SysNotice> selectNoticeList(SysNotice notice);

    /**
     * The new announcement
     * 
     * @param notice Announcement of the information
     * @return The results of
     */
    int insertNotice(SysNotice notice);

    /**
     * Modify bulletin
     * 
     * @param notice Announcement of the information
     * @return The results of
     */
    int updateNotice(SysNotice notice);

    /**
     * Deleting a Bulletin
     * 
     * @param noticeId The announcementID
     * @return The results of
     */
    int deleteNoticeById(Long noticeId);
    
    /**
     * Delete bulletins in batches
     * 
     * @param noticeIds Bulletin to deleteID
     * @return The results of
     */
    int deleteNoticeByIds(Long[] noticeIds);
}
