package com.ojas.hiring.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ojas.hiring.entity.PMO;
import com.ojas.hiring.repo.PMORepo;
import com.ojas.hiring.service.PMOService;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@Service
public class PMOServiceImpl implements PMOService {

	public static Cell CELL0;
	public static Cell CELL1;
	public static Cell CELL2;
	public static Cell CELL3;
	public static Cell CELL4;
	public static Cell CELL5;
	public static Cell CELL6;
	public static Cell CELL7;
	public static Cell CELL8;
	public static Cell CELL9;
	public static Cell CELL10;
	public static Cell CELL11;

	public static String EMPLOYEE_ID = null;
	public static String EMPLOYEE_NAME = null;
	public static String EMPLOYEE_TYPE = null;
	public static String PROJECT_CATAGORY = null;
	public static String CLIENT_NAME = null;
	public static String PROJECT_NAME = null;
	public static String PROJECT_STATUS = null;
	public static String PROJECT_START_DATE = null;
	public static String PROJECT_END_DATE = null;
	public static String DEPARTMENT = null;
	public static String NETWORKING_DAYS = null;
	public static String REMARKS = null;

	public static String EMPLOYEE_ID_FORMAT = null;

	public static Row ROW;
	public static Workbook WORKBOOK;

	@Autowired
	private PMORepo repo;

	@Override
	public PMO savePmo(PMO pmo) {

		return repo.save(pmo);
	}

	@Override
	public List<PMO> getAllPmo() {

		return repo.findAll();
	}

	@Override
	public String deleteAllPmo() {

		repo.deleteAll();

		return "Delete all PMO Deatils completed Successfully";
	}

	@Override
	public PMO updatePmo(String pmoId, PMO pmo) {
		Optional<PMO> pmoIdFromDb = repo.findById(Integer.parseInt(pmoId));
		if (pmoIdFromDb.isPresent()) {
			PMO pmoEntity = new PMO();
			pmoEntity.setPmoId(Integer.parseInt(pmoId));
			pmoEntity.setEmployeeId(pmo.getEmployeeId());
			pmoEntity.setEmployeeName(pmo.getEmployeeName());
			pmoEntity.setEmployeeType(pmo.getEmployeeType());
			pmoEntity.setProjectCategory(pmo.getProjectName());
			pmoEntity.setClientName(pmo.getClientName());
			pmoEntity.setProjectName(pmo.getProjectName());
			pmoEntity.setProjectStatus(pmo.getProjectStatus());
			pmoEntity.setProjectStartDate(pmo.getProjectStartDate());
			pmoEntity.setProjectEndDate(pmo.getProjectEndDate());
			pmoEntity.setDepartment(pmo.getDepartment());
			pmoEntity.setNetWorkingDays(pmo.getNetWorkingDays());
			pmoEntity.setRemarks(pmo.getRemarks());
			repo.save(pmoEntity);
			return pmoEntity;
		}
		return null;
	}

	@Override
	public void bulkUpload(MultipartFile excelFile) throws IOException {
		String extension = FilenameUtils.getExtension(excelFile.getOriginalFilename());
		if (extension.equalsIgnoreCase("xls")) {
			readingDataFromXlsFile(excelFile);
		} else {
			readingDataFromXlsxFile(excelFile);
		}

	}

	public void readingDataFromXlsFile(MultipartFile excelFile) throws IOException {

		Workbook workbook = new HSSFWorkbook(excelFile.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			ROW = (Row) sheet.getRow(i + 1);

			CELL0 = ROW.getCell(0, Row.RETURN_NULL_AND_BLANK);

			EMPLOYEE_ID = ROW.getCell(0).toString();

			EMPLOYEE_ID_FORMAT = EMPLOYEE_ID.substring(0, EMPLOYEE_ID.length() - 2);

			if (EMPLOYEE_ID != null) {
				Optional<PMO> employeeId = repo.findByEmployeeId(EMPLOYEE_ID_FORMAT);
				if (employeeId.isPresent()) {

					CELL1 = ROW.getCell(1, Row.RETURN_NULL_AND_BLANK);

					EMPLOYEE_NAME = ROW.getCell(1).toString();

					CELL2 = ROW.getCell(2, Row.RETURN_NULL_AND_BLANK);

					EMPLOYEE_TYPE = ROW.getCell(2).toString();

					if (EMPLOYEE_TYPE != null) {
						if (!employeeId.get().getEmployeeType().contains(EMPLOYEE_TYPE)
								|| !employeeId.get().getEmployeeType().equalsIgnoreCase(EMPLOYEE_TYPE)) {
							EMPLOYEE_TYPE = EMPLOYEE_TYPE + "<br>" + employeeId.get().getEmployeeType();
						} else {
							EMPLOYEE_TYPE = ROW.getCell(2).toString();
						}
					}

					CELL3 = ROW.getCell(3, Row.RETURN_NULL_AND_BLANK);

					PROJECT_CATAGORY = ROW.getCell(3).toString();

					if (PROJECT_CATAGORY != null) {
						if (!employeeId.get().getProjectCategory().contains(PROJECT_CATAGORY)
								|| !employeeId.get().getProjectCategory().equalsIgnoreCase(PROJECT_CATAGORY)) {
							PROJECT_CATAGORY = PROJECT_CATAGORY + "<br>" + employeeId.get().getProjectCategory();
						} else {
							PROJECT_CATAGORY = ROW.getCell(3).toString();
						}

					}

					CELL4 = ROW.getCell(4, Row.RETURN_NULL_AND_BLANK);

					CLIENT_NAME = ROW.getCell(4).toString();

					if (CLIENT_NAME != null) {
						if (!employeeId.get().getClientName().contains(CLIENT_NAME)
								|| !employeeId.get().getClientName().equalsIgnoreCase(CLIENT_NAME)) {
							CLIENT_NAME = CLIENT_NAME + "<br>" + employeeId.get().getClientName();
						} else {
							CLIENT_NAME = ROW.getCell(4).toString();
						}

					}

					CELL5 = ROW.getCell(5, Row.RETURN_NULL_AND_BLANK);

					PROJECT_NAME = ROW.getCell(5).toString();

					if (PROJECT_NAME != null) {

						if (!employeeId.get().getProjectName().contains(PROJECT_NAME)
								|| !employeeId.get().getProjectName().equalsIgnoreCase(PROJECT_NAME)) {
							PROJECT_NAME = PROJECT_NAME + "<br>" + employeeId.get().getProjectName();
						} else {
							PROJECT_NAME = ROW.getCell(5).toString();
						}

					}

					CELL6 = ROW.getCell(6, Row.RETURN_NULL_AND_BLANK);

					PROJECT_STATUS = ROW.getCell(6).toString();

					if (PROJECT_STATUS != null) {

						if (!employeeId.get().getProjectStatus().contains(PROJECT_STATUS)
								|| !employeeId.get().getProjectStatus().equalsIgnoreCase(PROJECT_STATUS)) {
							PROJECT_STATUS = PROJECT_STATUS + "<br>" + employeeId.get().getProjectStatus();
						} else {
							PROJECT_STATUS = ROW.getCell(6).toString();
						}

					}

					CELL7 = ROW.getCell(7, Row.RETURN_NULL_AND_BLANK);

					PROJECT_START_DATE = ROW.getCell(7).toString();

					if (PROJECT_START_DATE != null) {

						if (!employeeId.get().getProjectStartDate().contains(PROJECT_START_DATE)
								|| !employeeId.get().getProjectStartDate().equalsIgnoreCase(PROJECT_START_DATE)) {
							PROJECT_START_DATE = PROJECT_START_DATE + "<br>" + employeeId.get().getProjectStartDate();
						} else {
							PROJECT_START_DATE = ROW.getCell(7).toString();
						}

					}

					CELL8 = ROW.getCell(8, Row.RETURN_NULL_AND_BLANK);

					PROJECT_END_DATE = ROW.getCell(8).toString();

					if (PROJECT_END_DATE != null) {

						if (!employeeId.get().getProjectEndDate().contains(PROJECT_END_DATE)
								|| !employeeId.get().getProjectEndDate().equalsIgnoreCase(PROJECT_END_DATE)) {
							PROJECT_END_DATE = PROJECT_END_DATE + "<br>" + employeeId.get().getProjectEndDate();
						} else {
							PROJECT_END_DATE = ROW.getCell(8).toString();
						}

					}

					CELL9 = ROW.getCell(9, Row.RETURN_NULL_AND_BLANK);

					DEPARTMENT = ROW.getCell(9).toString();

					if (DEPARTMENT != null) {

						if (!employeeId.get().getDepartment().contains(DEPARTMENT)
								|| !employeeId.get().getDepartment().equalsIgnoreCase(DEPARTMENT)) {
							DEPARTMENT = DEPARTMENT + "<br>" + employeeId.get().getDepartment();
						} else {
							DEPARTMENT = ROW.getCell(9).toString();
						}

					}

					CELL10 = ROW.getCell(10, Row.RETURN_NULL_AND_BLANK);

					String netWorkingDays = ROW.getCell(10).toString();
					NETWORKING_DAYS = netWorkingDays.substring(0, netWorkingDays.length() - 2);
					if (NETWORKING_DAYS != null) {

						if (!employeeId.get().getNetWorkingDays().contains(NETWORKING_DAYS)
								|| !employeeId.get().getNetWorkingDays().equalsIgnoreCase(NETWORKING_DAYS)) {

							NETWORKING_DAYS = NETWORKING_DAYS + "<br>" + employeeId.get().getNetWorkingDays();

						} else {
							NETWORKING_DAYS = ROW.getCell(10).toString();
						}

					}

					CELL11 = ROW.getCell(11, Row.RETURN_NULL_AND_BLANK);

					REMARKS = ROW.getCell(11).toString();

					if (REMARKS != null) {

						if (!employeeId.get().getRemarks().contains(REMARKS)
								|| employeeId.get().getRemarks().equalsIgnoreCase(REMARKS)) {
							REMARKS = REMARKS + "<br>" + employeeId.get().getRemarks();
						} else {

							REMARKS = ROW.getCell(11).toString();
						}

					}

					PMO pmoEntity = new PMO();
					pmoEntity.setPmoId(employeeId.get().getPmoId());
					pmoEntity.setEmployeeId(employeeId.get().getEmployeeId());
					pmoEntity.setEmployeeName(EMPLOYEE_NAME);
					pmoEntity.setEmployeeType(EMPLOYEE_TYPE);
					pmoEntity.setProjectCategory(PROJECT_CATAGORY);
					pmoEntity.setClientName(CLIENT_NAME);
					pmoEntity.setProjectName(PROJECT_NAME);
					pmoEntity.setProjectStatus(PROJECT_STATUS);
					pmoEntity.setProjectStartDate(PROJECT_START_DATE);
					pmoEntity.setProjectEndDate(PROJECT_END_DATE);
					pmoEntity.setDepartment(DEPARTMENT);
					pmoEntity.setNetWorkingDays(NETWORKING_DAYS);
					pmoEntity.setRemarks(REMARKS);
					repo.save(pmoEntity);

				} else {

					CELL1 = ROW.getCell(1, Row.RETURN_NULL_AND_BLANK);

					EMPLOYEE_NAME = ROW.getCell(1).toString();

					CELL2 = ROW.getCell(2, Row.RETURN_NULL_AND_BLANK);

					EMPLOYEE_TYPE = ROW.getCell(2).toString();

					CELL3 = ROW.getCell(3, Row.RETURN_NULL_AND_BLANK);

					PROJECT_CATAGORY = ROW.getCell(3).toString();

					CELL4 = ROW.getCell(4, Row.RETURN_NULL_AND_BLANK);

					CLIENT_NAME = ROW.getCell(4).toString();

					CELL5 = ROW.getCell(5, Row.RETURN_NULL_AND_BLANK);

					PROJECT_NAME = ROW.getCell(5).toString();

					CELL6 = ROW.getCell(6, Row.RETURN_NULL_AND_BLANK);

					PROJECT_STATUS = ROW.getCell(6).toString();

					CELL7 = ROW.getCell(7, Row.RETURN_NULL_AND_BLANK);

					PROJECT_START_DATE = ROW.getCell(7).toString();

					CELL8 = ROW.getCell(8, Row.RETURN_NULL_AND_BLANK);

					PROJECT_END_DATE = ROW.getCell(8).toString();

					CELL9 = ROW.getCell(9, Row.RETURN_NULL_AND_BLANK);

					DEPARTMENT = ROW.getCell(9).toString();

					CELL10 = ROW.getCell(10, Row.RETURN_NULL_AND_BLANK);

					NETWORKING_DAYS = ROW.getCell(10).toString();

					CELL11 = ROW.getCell(11, Row.RETURN_NULL_AND_BLANK);

					REMARKS = ROW.getCell(11).toString();

					PMO pmoEntity = new PMO();
					pmoEntity.setEmployeeId(EMPLOYEE_ID.substring(0, EMPLOYEE_ID.length() - 2));
					pmoEntity.setEmployeeName(EMPLOYEE_NAME);
					pmoEntity.setEmployeeType(EMPLOYEE_TYPE);
					pmoEntity.setProjectCategory(PROJECT_CATAGORY);
					pmoEntity.setClientName(CLIENT_NAME);
					pmoEntity.setProjectName(PROJECT_NAME);
					pmoEntity.setProjectStatus(PROJECT_STATUS);
					pmoEntity.setProjectStartDate(PROJECT_START_DATE);
					pmoEntity.setProjectEndDate(PROJECT_END_DATE);
					pmoEntity.setDepartment(DEPARTMENT);
					pmoEntity.setNetWorkingDays(NETWORKING_DAYS.substring(0, NETWORKING_DAYS.length() - 2));
					pmoEntity.setRemarks(REMARKS);
					repo.save(pmoEntity);
				}
			}
		}

	}

	public void readingDataFromXlsxFile(MultipartFile excelFile) throws IOException {
		WORKBOOK = new XSSFWorkbook(excelFile.getInputStream());

		for (int sheetIndex = 0; sheetIndex < WORKBOOK.getNumberOfSheets(); sheetIndex++) {

			XSSFSheet sheet = (XSSFSheet) WORKBOOK.getSheetAt(sheetIndex);
			String sheetName = sheet.getSheetName();

			for (int i = 0; i < sheet.getLastRowNum() - 1; i++) {
				ROW = (Row) sheet.getRow(i + 1);

				CELL0 = ROW.getCell(0, Row.RETURN_NULL_AND_BLANK);

				EMPLOYEE_ID = ROW.getCell(0).toString();
				EMPLOYEE_ID_FORMAT = EMPLOYEE_ID.substring(0, EMPLOYEE_ID.length() - 2);
				if (EMPLOYEE_ID != null) {
					Optional<PMO> employeeId = repo.findByEmployeeId(EMPLOYEE_ID_FORMAT);
					if (employeeId.isPresent()) {

						CELL1 = ROW.getCell(1, Row.RETURN_NULL_AND_BLANK);

						EMPLOYEE_NAME = ROW.getCell(1).toString();

						CELL2 = ROW.getCell(2, Row.RETURN_NULL_AND_BLANK);

						EMPLOYEE_TYPE = ROW.getCell(2).toString();

						if (EMPLOYEE_TYPE != null) {
							if (!employeeId.get().getEmployeeType().contains(EMPLOYEE_TYPE)
									|| !employeeId.get().getEmployeeType().equalsIgnoreCase(EMPLOYEE_TYPE)) {
								EMPLOYEE_TYPE = EMPLOYEE_TYPE + "<br>" + employeeId.get().getEmployeeType();
							} else {
								EMPLOYEE_TYPE = ROW.getCell(2).toString();
							}
						}

						CELL3 = ROW.getCell(3, Row.RETURN_NULL_AND_BLANK);

						PROJECT_CATAGORY = ROW.getCell(3).toString();

						if (PROJECT_CATAGORY != null) {
							if (!employeeId.get().getProjectCategory().contains(PROJECT_CATAGORY)
									|| !employeeId.get().getProjectCategory().equalsIgnoreCase(PROJECT_CATAGORY)) {
								PROJECT_CATAGORY = PROJECT_CATAGORY + "<br>" + employeeId.get().getProjectCategory();
							} else {
								PROJECT_CATAGORY = ROW.getCell(3).toString();
							}

						}

						CELL4 = ROW.getCell(4, Row.RETURN_NULL_AND_BLANK);

						CLIENT_NAME = ROW.getCell(4).toString();

						if (CLIENT_NAME != null) {
							if (!employeeId.get().getClientName().contains(CLIENT_NAME)
									|| !employeeId.get().getClientName().equalsIgnoreCase(CLIENT_NAME)) {
								CLIENT_NAME = CLIENT_NAME + "<br>" + employeeId.get().getClientName();
							} else {
								CLIENT_NAME = ROW.getCell(4).toString();
							}

						}

						CELL5 = ROW.getCell(5, Row.RETURN_NULL_AND_BLANK);

						PROJECT_NAME = ROW.getCell(5).toString();

						if (PROJECT_NAME != null) {

							if (!employeeId.get().getProjectName().contains(PROJECT_NAME)
									|| !employeeId.get().getProjectName().equalsIgnoreCase(PROJECT_NAME)) {
								PROJECT_NAME = PROJECT_NAME + "<br>" + employeeId.get().getProjectName();
							} else {
								PROJECT_NAME = ROW.getCell(5).toString();
							}

						}

						CELL6 = ROW.getCell(6, Row.RETURN_NULL_AND_BLANK);

						PROJECT_STATUS = ROW.getCell(6).toString();

						if (PROJECT_STATUS != null) {

							if (!employeeId.get().getProjectStatus().contains(PROJECT_STATUS)
									|| !employeeId.get().getProjectStatus().equalsIgnoreCase(PROJECT_STATUS)) {
								PROJECT_STATUS = PROJECT_STATUS + "<br>" + employeeId.get().getProjectStatus();
							} else {
								PROJECT_STATUS = ROW.getCell(6).toString();
							}

						}

						CELL7 = ROW.getCell(7, Row.RETURN_NULL_AND_BLANK);

						PROJECT_START_DATE = ROW.getCell(7).toString();

						if (PROJECT_START_DATE != null) {

							if (!employeeId.get().getProjectStartDate().contains(PROJECT_START_DATE)
									|| !employeeId.get().getProjectStartDate().equalsIgnoreCase(PROJECT_START_DATE)) {
								PROJECT_START_DATE = PROJECT_START_DATE + "<br>"
										+ employeeId.get().getProjectStartDate();
							} else {
								PROJECT_START_DATE = ROW.getCell(7).toString();
							}

						}

						CELL8 = ROW.getCell(8, Row.RETURN_NULL_AND_BLANK);

						PROJECT_END_DATE = ROW.getCell(8).toString();

						if (PROJECT_END_DATE != null) {

							if (!employeeId.get().getProjectEndDate().contains(PROJECT_END_DATE)
									|| !employeeId.get().getProjectEndDate().equalsIgnoreCase(PROJECT_END_DATE)) {
								PROJECT_END_DATE = PROJECT_END_DATE + "<br>" + employeeId.get().getProjectEndDate();
							} else {
								PROJECT_END_DATE = ROW.getCell(8).toString();
							}

						}

						CELL9 = ROW.getCell(9, Row.RETURN_NULL_AND_BLANK);

						DEPARTMENT = ROW.getCell(9).toString();

						if (DEPARTMENT != null) {

							if (!employeeId.get().getDepartment().contains(DEPARTMENT)
									|| !employeeId.get().getDepartment().equalsIgnoreCase(DEPARTMENT)) {
								DEPARTMENT = DEPARTMENT + "<br>" + employeeId.get().getDepartment();
							} else {
								DEPARTMENT = ROW.getCell(9).toString();
							}

						}

						CELL10 = ROW.getCell(10, Row.RETURN_NULL_AND_BLANK);

						String netWorkingDays = ROW.getCell(10).toString();
						NETWORKING_DAYS = netWorkingDays.substring(0, netWorkingDays.length() - 2);
						if (NETWORKING_DAYS != null) {

							if (!employeeId.get().getNetWorkingDays().contains(NETWORKING_DAYS)
									|| !employeeId.get().getNetWorkingDays().equalsIgnoreCase(NETWORKING_DAYS)) {

								NETWORKING_DAYS = NETWORKING_DAYS + "<br>" + employeeId.get().getNetWorkingDays();

							} else {
								NETWORKING_DAYS = ROW.getCell(10).toString();
							}

						}

						CELL11 = ROW.getCell(11, Row.RETURN_NULL_AND_BLANK);

						REMARKS = ROW.getCell(11).toString();

						if (REMARKS != null) {

							if (!employeeId.get().getRemarks().contains(REMARKS)
									|| employeeId.get().getRemarks().equalsIgnoreCase(REMARKS)) {
								REMARKS = REMARKS + "<br>" + employeeId.get().getRemarks();
							} else {

								REMARKS = ROW.getCell(11).toString();
							}

						}

						PMO pmoEntity = new PMO();
						pmoEntity.setPmoId(employeeId.get().getPmoId());
						pmoEntity.setEmployeeId(employeeId.get().getEmployeeId());
						pmoEntity.setEmployeeName(EMPLOYEE_NAME);
						pmoEntity.setEmployeeType(EMPLOYEE_TYPE);
						pmoEntity.setProjectCategory(PROJECT_CATAGORY);
						pmoEntity.setClientName(CLIENT_NAME);
						pmoEntity.setProjectName(PROJECT_NAME);
						pmoEntity.setProjectStatus(PROJECT_STATUS);
						pmoEntity.setProjectStartDate(PROJECT_START_DATE);
						pmoEntity.setProjectEndDate(PROJECT_END_DATE);
						pmoEntity.setDepartment(DEPARTMENT);
						pmoEntity.setOptionalSheetNameOfExcel(sheetName);
						long millis = System.currentTimeMillis();
						java.sql.Date date = new java.sql.Date(millis);
						pmoEntity.setPublishedOn(date);

						pmoEntity.setNetWorkingDays(NETWORKING_DAYS);
						pmoEntity.setRemarks(REMARKS);
						repo.save(pmoEntity);

					} else {

						CELL1 = ROW.getCell(1, Row.RETURN_NULL_AND_BLANK);

						EMPLOYEE_NAME = ROW.getCell(1).toString();

						CELL2 = ROW.getCell(2, Row.RETURN_NULL_AND_BLANK);

						EMPLOYEE_TYPE = ROW.getCell(2).toString();

						CELL3 = ROW.getCell(3, Row.RETURN_NULL_AND_BLANK);

						PROJECT_CATAGORY = ROW.getCell(3).toString();

						CELL4 = ROW.getCell(4, Row.RETURN_NULL_AND_BLANK);

						CLIENT_NAME = ROW.getCell(4).toString();

						CELL5 = ROW.getCell(5, Row.RETURN_NULL_AND_BLANK);

						PROJECT_NAME = ROW.getCell(5).toString();

						CELL6 = ROW.getCell(6, Row.RETURN_NULL_AND_BLANK);

						PROJECT_STATUS = ROW.getCell(6).toString();

						CELL7 = ROW.getCell(7, Row.RETURN_NULL_AND_BLANK);

						PROJECT_START_DATE = ROW.getCell(7).toString();

						CELL8 = ROW.getCell(8, Row.RETURN_NULL_AND_BLANK);

						PROJECT_END_DATE = ROW.getCell(8).toString();

						CELL9 = ROW.getCell(9, Row.RETURN_NULL_AND_BLANK);

						DEPARTMENT = ROW.getCell(9).toString();

						CELL10 = ROW.getCell(10, Row.RETURN_NULL_AND_BLANK);

						NETWORKING_DAYS = ROW.getCell(10).toString();

						CELL11 = ROW.getCell(11, Row.RETURN_NULL_AND_BLANK);

						REMARKS = ROW.getCell(11).toString();

						PMO pmoEntity = new PMO();
						pmoEntity.setEmployeeId(EMPLOYEE_ID.substring(0, EMPLOYEE_ID.length() - 2));
						pmoEntity.setEmployeeName(EMPLOYEE_NAME);
						pmoEntity.setEmployeeType(EMPLOYEE_TYPE);
						pmoEntity.setProjectCategory(PROJECT_CATAGORY);
						pmoEntity.setClientName(CLIENT_NAME);
						pmoEntity.setProjectName(PROJECT_NAME);
						pmoEntity.setProjectStatus(PROJECT_STATUS);
						pmoEntity.setProjectStartDate(PROJECT_START_DATE);
						pmoEntity.setProjectEndDate(PROJECT_END_DATE);
						pmoEntity.setDepartment(DEPARTMENT);
						pmoEntity.setNetWorkingDays(NETWORKING_DAYS.substring(0, NETWORKING_DAYS.length() - 2));
						pmoEntity.setRemarks(REMARKS);
						pmoEntity.setOptionalSheetNameOfExcel(sheetName);
						long millis = System.currentTimeMillis();
						java.sql.Date date = new java.sql.Date(millis);
						pmoEntity.setPublishedOn(date);
						repo.save(pmoEntity);
					}
				}
			}
		}

	}
}
