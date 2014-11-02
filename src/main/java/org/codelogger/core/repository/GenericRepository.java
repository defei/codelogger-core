package org.codelogger.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

public interface GenericRepository <T, I extends Serializable>
    extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {

}
