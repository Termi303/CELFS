/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bris.celfs.database;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CriterionRepository extends CrudRepository<Criterion, Long> {
    public List<Criterion> findByCategoryId(Long categoryId);
}
