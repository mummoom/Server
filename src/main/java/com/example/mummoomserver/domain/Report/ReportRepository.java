package com.example.mummoomserver.domain.Report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsByUser_userIdxAndPost_postIdx(long userIdx, long postIdx);

    boolean existsByUser_userIdxAndComment_commentIdx(long userIdx, long commendIdx);
}
