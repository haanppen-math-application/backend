package com.hpmath.common.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

interface EventOutboxRepository extends JpaRepository<Outbox, Long> {
}
