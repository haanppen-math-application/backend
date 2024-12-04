package com.hanpyeon.academyapi.online;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface OnlineStudentRepository extends JpaRepository<OnlineStudent, Long> {
}
