package com.example.mummoomserver.domain.Report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Report findByReportIdx(long reportIdx);

    boolean existsByUser_userIdxAndPost_postIdx(long userIdx, long postIdx);

    boolean existsByUser_userIdxAndComment_commentIdx(long userIdx, long commentIdx);

    boolean existsByUser_UserIdxAndReportedUser_UserIdxAndIsBlockedIsTrue(long userIdx, long reportedUserIdx);
}
