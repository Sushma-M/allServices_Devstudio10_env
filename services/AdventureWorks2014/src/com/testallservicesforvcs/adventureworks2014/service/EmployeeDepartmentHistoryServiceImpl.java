/*Copyright (c) 2016-2017 vcstest4.com All Rights Reserved.
 This software is the confidential and proprietary information of vcstest4.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with vcstest4.com*/
package com.testallservicesforvcs.adventureworks2014.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.wavemaker.commons.InvalidInputException;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.testallservicesforvcs.adventureworks2014.EmployeeDepartmentHistory;
import com.testallservicesforvcs.adventureworks2014.EmployeeDepartmentHistoryId;


/**
 * ServiceImpl object for domain model class EmployeeDepartmentHistory.
 *
 * @see EmployeeDepartmentHistory
 */
@Service("AdventureWorks2014.EmployeeDepartmentHistoryService")
@Validated
public class EmployeeDepartmentHistoryServiceImpl implements EmployeeDepartmentHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDepartmentHistoryServiceImpl.class);


    @Autowired
    @Qualifier("AdventureWorks2014.EmployeeDepartmentHistoryDao")
    private WMGenericDao<EmployeeDepartmentHistory, EmployeeDepartmentHistoryId> wmGenericDao;

    @Autowired
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<EmployeeDepartmentHistory, EmployeeDepartmentHistoryId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AdventureWorks2014TransactionManager")
    @Override
    public EmployeeDepartmentHistory create(EmployeeDepartmentHistory employeeDepartmentHistory) {
        LOGGER.debug("Creating a new EmployeeDepartmentHistory with information: {}", employeeDepartmentHistory);

        EmployeeDepartmentHistory employeeDepartmentHistoryCreated = this.wmGenericDao.create(employeeDepartmentHistory);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(employeeDepartmentHistoryCreated);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public EmployeeDepartmentHistory getById(EmployeeDepartmentHistoryId employeedepartmenthistoryId) {
        LOGGER.debug("Finding EmployeeDepartmentHistory by id: {}", employeedepartmenthistoryId);
        return this.wmGenericDao.findById(employeedepartmenthistoryId);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public EmployeeDepartmentHistory findById(EmployeeDepartmentHistoryId employeedepartmenthistoryId) {
        LOGGER.debug("Finding EmployeeDepartmentHistory by id: {}", employeedepartmenthistoryId);
        try {
            return this.wmGenericDao.findById(employeedepartmenthistoryId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No EmployeeDepartmentHistory found with id: {}", employeedepartmenthistoryId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public List<EmployeeDepartmentHistory> findByMultipleIds(List<EmployeeDepartmentHistoryId> employeedepartmenthistoryIds, boolean orderedReturn) {
        LOGGER.debug("Finding EmployeeDepartmentHistories by ids: {}", employeedepartmenthistoryIds);

        return this.wmGenericDao.findByMultipleIds(employeedepartmenthistoryIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AdventureWorks2014TransactionManager")
    @Override
    public EmployeeDepartmentHistory update(EmployeeDepartmentHistory employeeDepartmentHistory) {
        LOGGER.debug("Updating EmployeeDepartmentHistory with information: {}", employeeDepartmentHistory);

        this.wmGenericDao.update(employeeDepartmentHistory);
        this.wmGenericDao.refresh(employeeDepartmentHistory);

        return employeeDepartmentHistory;
    }
    
    @Transactional(value = "AdventureWorks2014TransactionManager")
    @Override
    public EmployeeDepartmentHistory partialUpdate(EmployeeDepartmentHistoryId employeedepartmenthistoryId, Map<String, Object>employeeDepartmentHistoryPatch) {
        LOGGER.debug("Partially Updating the EmployeeDepartmentHistory with id: {}", employeedepartmenthistoryId);
        
        EmployeeDepartmentHistory employeeDepartmentHistory = getById(employeedepartmenthistoryId);

        try {
            ObjectReader employeeDepartmentHistoryReader = this.objectMapper.readerForUpdating(employeeDepartmentHistory);
            employeeDepartmentHistory = employeeDepartmentHistoryReader.readValue(this.objectMapper.writeValueAsString(employeeDepartmentHistoryPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", employeeDepartmentHistoryPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        employeeDepartmentHistory = update(employeeDepartmentHistory);

        return employeeDepartmentHistory;
    }

    @Transactional(value = "AdventureWorks2014TransactionManager")
    @Override
    public EmployeeDepartmentHistory delete(EmployeeDepartmentHistoryId employeedepartmenthistoryId) {
        LOGGER.debug("Deleting EmployeeDepartmentHistory with id: {}", employeedepartmenthistoryId);
        EmployeeDepartmentHistory deleted = this.wmGenericDao.findById(employeedepartmenthistoryId);
        if (deleted == null) {
            LOGGER.debug("No EmployeeDepartmentHistory found with id: {}", employeedepartmenthistoryId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), EmployeeDepartmentHistory.class.getSimpleName(), employeedepartmenthistoryId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AdventureWorks2014TransactionManager")
    @Override
    public void delete(EmployeeDepartmentHistory employeeDepartmentHistory) {
        LOGGER.debug("Deleting EmployeeDepartmentHistory with {}", employeeDepartmentHistory);
        this.wmGenericDao.delete(employeeDepartmentHistory);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Page<EmployeeDepartmentHistory> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all EmployeeDepartmentHistories");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Page<EmployeeDepartmentHistory> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all EmployeeDepartmentHistories");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AdventureWorks2014 for table EmployeeDepartmentHistory to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AdventureWorks2014 for table EmployeeDepartmentHistory to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}