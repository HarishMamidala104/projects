package com.ojas.hiring.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ojas.hiring.entity.HireTable;
import com.ojas.hiring.entity.Interviews;
import com.ojas.hiring.repo.HireTableRepo;
import com.ojas.hiring.service.HireTableService;

@Service
public class HireTableServiceImpl implements HireTableService {

	@Autowired
	private HireTableRepo hireTableRepo;
	@Autowired
	private EntityManager entityManager;

	@Value("${db.name}")
	private String dbName;

	@Override
	public HireTable addInterviewDetails(HireTable hireTable) {
		return hireTableRepo.save(hireTable);
	}

	@Override
	public List<HireTable> getAllInterviewDetails() {
		return hireTableRepo.findAll();
	}

	@Override
	public ResponseEntity<Object> findInterviewById(int id) {
		List<HireTable> getHireDetails = hireTableRepo.findInterviewById(id);
		String msg = "Id is not found..!";
		if (getHireDetails.isEmpty()) {
			return new ResponseEntity<Object>(msg, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Object>(getHireDetails, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Object> findByInterviewByHireId(String hireId) {
		List<HireTable> getHireDetails = hireTableRepo.findInterviewByHireId(hireId);
		String msg = "Id is not found..!";
		if (getHireDetails.isEmpty()) {
			return new ResponseEntity<Object>(msg, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Object>(getHireDetails, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Object> findByInterviewByToken(String id) {
		List<HireTable> getHireDetails = hireTableRepo.findInterviewByToken(id);
		String msg = "Only Scheduled.No Interviews Taken";
		if (getHireDetails.isEmpty()) {
			return new ResponseEntity<Object>(msg, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Object>(getHireDetails, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<Object> updateHireeDetails(HireTable hireTable, int hid) {
		System.out.println(hireTable);
		Optional<HireTable> hireeDetail = hireTableRepo.findById(hid);
		if (hireeDetail.isPresent()) {
			String message = "Unknown Hire Id";
			return new ResponseEntity<Object>(message, HttpStatus.OK);
		} else {
			HireTable hireeUpdater = hireTableRepo.findById(hid).get();
			if (hireTable.getHid() == 0) {
				int addId = hireeDetail.get().getHid();
				hireeUpdater.setHid(addId);
			} else {
				hireeUpdater.setHid(hireTable.getHid());
			}
			if (hireTable.getComment() == null) {
				String temp = hireeDetail.get().getComment();
				hireeUpdater.setComment(temp);
			} else {
				hireeUpdater.setComment(hireTable.getComment());
			}
			if (hireTable.getHireId() == null) {
				String temp = hireeDetail.get().getHireId();
				hireeUpdater.setHireId(temp);
			} else {
				hireeUpdater.setHireId(hireTable.getHireId());
			}
			if (hireTable.getInterviewer() == null) {
				String temp = hireeDetail.get().getInterviewer();
				hireeUpdater.setInterviewer(temp);
			} else {
				hireeUpdater.setInterviewer(hireTable.getInterviewer());
			}
			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);
			hireeUpdater.setPublishedDate(date);

			if (hireTable.getRoleDescription() == null) {
				String temp = hireeDetail.get().getRoleDescription();
				hireeUpdater.setRoleDescription(temp);
			} else {
				hireeUpdater.setRoleDescription(hireTable.getRoleDescription());
			}
			if (hireTable.getStatus() == null) {
				String temp = hireeDetail.get().getStatus();
				hireeUpdater.setStatus(temp);
			} else {
				hireeUpdater.setStatus(hireTable.getStatus());
			}
			if (hireTable.getToken() == null) {
				String temp = hireeDetail.get().getToken();
				hireeUpdater.setToken(temp);
			} else {
				hireeUpdater.setToken(hireTable.getToken());
			}
			if (hireTable.getRating() == 0) {
				int t = hireeDetail.get().getRating();
				hireeUpdater.setRating(t);
			} else {
				hireeUpdater.setRating(hireTable.getRating());
			}
			hireeUpdater.setVisibility(1);
			hireTableRepo.save(hireeUpdater);
			return new ResponseEntity<Object>(hireeUpdater, HttpStatus.OK);
		}

	}

	@Override
	public ResponseEntity<Object> deleteHiree(HireTable hireTable, int hid) {
		System.out.println(hireTable);
		Optional<HireTable> hireeDetail = hireTableRepo.findById(hid);
		if (hireeDetail.isPresent()) {
			String message = "Unknown Hire Id";
			return new ResponseEntity<Object>(message, HttpStatus.OK);
		} else {
			HireTable hireeUpdater = hireTableRepo.findById(hid).get();
			if (hireTable.getHid() == 0) {
				int addId = hireeDetail.get().getHid();
				hireeUpdater.setHid(addId);
			} else {
				hireeUpdater.setHid(hireTable.getHid());
			}
			if (hireTable.getComment() == null) {
				String temp = hireeDetail.get().getComment();
				hireeUpdater.setComment(temp);
			} else {
				hireeUpdater.setComment(hireTable.getComment());
			}
			if (hireTable.getHireId() == null) {
				String temp = hireeDetail.get().getHireId();
				hireeUpdater.setHireId(temp);
			} else {
				hireeUpdater.setHireId(hireTable.getHireId());
			}
			if (hireTable.getInterviewer() == null) {
				String temp = hireeDetail.get().getInterviewer();
				hireeUpdater.setInterviewer(temp);
			} else {
				hireeUpdater.setInterviewer(hireTable.getInterviewer());
			}
			long millis = System.currentTimeMillis();
			java.sql.Date date = new java.sql.Date(millis);
			hireeUpdater.setPublishedDate(date);

			if (hireTable.getRoleDescription() == null) {
				String temp = hireeDetail.get().getRoleDescription();
				hireeUpdater.setRoleDescription(temp);
			} else {
				hireeUpdater.setRoleDescription(hireTable.getRoleDescription());
			}
			if (hireTable.getStatus() == null) {
				String temp = hireeDetail.get().getStatus();
				hireeUpdater.setStatus(temp);
			} else {
				hireeUpdater.setStatus(hireTable.getStatus());
			}
			if (hireTable.getToken() == null) {
				String temp = hireeDetail.get().getToken();
				hireeUpdater.setToken(temp);
			} else {
				hireeUpdater.setToken(hireTable.getToken());
			}
			if (hireTable.getRating() == 0) {
				int t = hireeDetail.get().getRating();
				hireeUpdater.setRating(t);
			} else {
				hireeUpdater.setRating(hireTable.getRating());
			}
			hireeUpdater.setVisibility(0);
			hireTableRepo.save(hireeUpdater);
			return new ResponseEntity<Object>(hireeUpdater, HttpStatus.OK);
		}

	}

	public ResponseEntity<Object> findByInterviewByTokenOpt(String id) {

		String preQuery = "SELECT COLUMN_NAME,COLUMN_TYPE,TABLE_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA='"
				+ dbName + "'";
		System.out.println(preQuery);
		List<Object[]> values = entityManager.createNativeQuery(preQuery).getResultList();
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> columnDataTypes = new ArrayList<String>();
		ArrayList<String> tableNames = new ArrayList<String>();
		String tmp = "";
		values.stream().forEach((record) -> {
			String str = (String) record[0];
			if (str.trim().equals("next_val")) {
			} else {
				columns.add(str);
				str = (String) record[1];
				columnDataTypes.add(str);
				str = (String) record[2];
				tableNames.add(str);
			}
		});

		ArrayList<String> t_columns = new ArrayList<String>();
		ArrayList<String> t_columnDataTypes = new ArrayList<String>();

		for (int i = 0; i < columns.size(); i++) {
			if (tableNames.get(i).trim().equals("data_candidate_interview_levels_information")) {
				tmp += "c." + columns.get(i) + ",";
				t_columns.add(columns.get(i));
				t_columnDataTypes.add(columnDataTypes.get(i));
			}
		}
		tmp = tmp.substring(0, tmp.length() - 1);
		t_columns.add("company");
		t_columnDataTypes.add("varchar");
		t_columns.add("candidate");
		t_columnDataTypes.add("varchar");
		t_columns.add("published_on");
		t_columnDataTypes.add("date");

		columns.clear();
		columnDataTypes.clear();

		String query = "select " + tmp + ",(select i.company from data_interviews_schedule_information i where i.id="
				+ id + ") as company,(select i.hire_name from data_interviews_schedule_information i where i.id=" + id
				+ ") as candidate,(select date(i.published_on) from data_interviews_schedule_information i where i.id="
				+ id + ") as published_on FROM data_candidate_interview_levels_information c  where c.token=" + id
				+ " and c.visibility=1 order by c.published_date asc";

		System.out.println(query);
		System.out.println(t_columns.size());
		System.out.println(t_columnDataTypes.size());

		List<Object[]> valuePairs = entityManager.createNativeQuery(query).getResultList();
		String data[] = new String[valuePairs.size()];
		for (int j = 0; j < data.length; j++) {
			data[j] = "";
		}

		for (int j = 0; j < t_columns.size(); j++) {
			int count = 0;
			for (Object[] dat : valuePairs) {
				String str = "";
				if (t_columnDataTypes.get(j).startsWith("varchar")) {
					str = (String) dat[j];
				}
				if (t_columnDataTypes.get(j).contains("text")) {
					str = (String) dat[j];
				}
				if (t_columnDataTypes.get(j).startsWith("bigint")) {
					str = "" + ((Long) dat[j]);
				}
				if (t_columnDataTypes.get(j).startsWith("int")) {
					str = "" + ((Integer) dat[j]);
				}
				if (t_columnDataTypes.get(j).startsWith("smallint")) {
					str = "" + ((Short) dat[j]);
				}
				if (t_columnDataTypes.get(j).startsWith("date")) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = formatter.parse("" + (dat[j]));
						str = (formatter.format(date));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (t_columnDataTypes.get(j).startsWith("datetime")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = sdf.parse("" + (dat[j]));
						str = date.toString();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (t_columnDataTypes.get(j).startsWith("timestamp")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = sdf.parse("" + (dat[j]));
						str = date.toString();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (t_columnDataTypes.get(j).startsWith("double")) {
					str = "" + ((Double) dat[j]);
				}
				// str = t_columns.get(j)+":"+t_columnDataTypes.get(j)+":"+str;
				str = "\"" + t_columns.get(j) + "\":\"" + str + "\",";
				data[count] += str;
				count++;
			}
			// String str = (String) valuePairs.get(j);
			// System.out.println(str);
		}
		String res = "[";
		for (int j = 0; j < data.length; j++) {
			data[j] = data[j].substring(0, data[j].length() - 1);
			if ((j + 1) == data.length) {
				res += "{" + data[j] + "}";
			} else {
				res += "{" + data[j] + "},";
			}

		}
//		

		res += "]";
		// System.out.println(res);
		return new ResponseEntity<Object>(res, HttpStatus.OK);
	}

	public ResponseEntity<Object> findInterviewRounds(String id, String hireId) {
		String preQuery = "SELECT COLUMN_NAME,COLUMN_TYPE,TABLE_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA='"
				+ dbName + "'";
		System.out.println(preQuery);
		List<Object[]> values = entityManager.createNativeQuery(preQuery).getResultList();
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> columnDataTypes = new ArrayList<String>();
		ArrayList<String> tableNames = new ArrayList<String>();
		String tmp = "";
		values.stream().forEach((record) -> {
			String str = (String) record[0];
			if (str.trim().equals("next_val")) {
			} else {
				columns.add(str);
				str = (String) record[1];
				columnDataTypes.add(str);
				str = (String) record[2];
				tableNames.add(str);
			}
		});

		ArrayList<String> t_columns = new ArrayList<String>();
		ArrayList<String> t_columnDataTypes = new ArrayList<String>();

		for (int i = 0; i < columns.size(); i++) {
			if (tableNames.get(i).trim().equals("data_candidate_interview_levels_information")) {
				tmp += "c." + columns.get(i) + ",";
				t_columns.add(columns.get(i));
				t_columnDataTypes.add(columnDataTypes.get(i));
			}
		}
		tmp = tmp.substring(0, tmp.length() - 1);

		columns.clear();
		columnDataTypes.clear();

		String query = "select * from data_candidate_interview_levels_information where token='" + id
				+ "' and hire_id='" + hireId + "' order by hid desc";

		System.out.println(query);
		System.out.println(t_columns.size());
		System.out.println(t_columnDataTypes.size());

		List<Object[]> valuePairs = entityManager.createNativeQuery(query).getResultList();
		String data[] = new String[valuePairs.size()];
		for (int j = 0; j < data.length; j++) {
			data[j] = "";
		}

		for (int j = 0; j < t_columns.size(); j++) {
			int count = 0;
			for (Object[] dat : valuePairs) {
				String str = "";
				if (t_columnDataTypes.get(j).startsWith("varchar")) {
					str = (String) dat[j];
				}
				if (t_columnDataTypes.get(j).contains("text")) {
					str = (String) dat[j];
				}
				if (t_columnDataTypes.get(j).startsWith("bigint")) {
					str = "" + ((Long) dat[j]);
				}
				if (t_columnDataTypes.get(j).startsWith("int")) {
					str = "" + ((Integer) dat[j]);
				}
				if (t_columnDataTypes.get(j).startsWith("smallint")) {
					str = "" + ((Short) dat[j]);
				}
				if (t_columnDataTypes.get(j).startsWith("date")) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = formatter.parse("" + (dat[j]));
						str = (formatter.format(date));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (t_columnDataTypes.get(j).startsWith("datetime")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = sdf.parse("" + (dat[j]));
						str = date.toString();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (t_columnDataTypes.get(j).startsWith("timestamp")) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = sdf.parse("" + (dat[j]));
						str = date.toString();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (t_columnDataTypes.get(j).startsWith("double")) {
					str = "" + ((Double) dat[j]);
				}
				// str = t_columns.get(j)+":"+t_columnDataTypes.get(j)+":"+str;
				str = "\"" + t_columns.get(j) + "\":\"" + str + "\",";
				data[count] += str;
				count++;
			}
			// String str = (String) valuePairs.get(j);
			// System.out.println(str);
		}
		String res = "[";
		for (int j = 0; j < data.length; j++) {
			data[j] = data[j].substring(0, data[j].length() - 1);
			if ((j + 1) == data.length) {
				res += "{" + data[j] + "}";
			} else {
				res += "{" + data[j] + "},";
			}

		}
//		

		res += "]";
		// System.out.println(res);
		return new ResponseEntity<Object>(res, HttpStatus.OK);
	}

}