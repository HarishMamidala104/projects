package com.ojas.hiring.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.ojas.hiring.dto.UploadsDto;
import com.ojas.hiring.entity.Candidate;
import com.ojas.hiring.entity.Uploads;

@Repository
public interface UploadsRepo extends JpaRepository<Uploads, Long> {

	@Query(value="select * from data_uploads where link=?1",nativeQuery = true)
	public Optional<Uploads> findByCid(long cid);
	

	@Query(value="select * from data_uploads where link=?1",nativeQuery = true)
	public Optional<Uploads> findByLink(long rid);
	
	@Query(value="select * from data_uploads where link=?1 and uploaded_module = ?2",nativeQuery = true)
	public Optional<Uploads> findByLinkAndModule(long link, String module);
	
	
	@Query(value="select * from data_uploads where uploaded_module = ?1 and  link=?2",nativeQuery = true)
	Optional<Uploads> getCandidateFileUploads(String uploaded_module, long link);
	
	
//	@Modifying
//    @Query("UPDATE Uploads u SET u.fileName = :fileName, u.extension = :fileExtension, " +
//           "u.fileSize = :fileSize, u.link = :link, u.uploadedDate = :uploadedDate" +
//           "WHERE u.uploadId = :id")
//    int updateUpload(@Param("fileName") String fileName,
//                     @Param("fileExtension") String fileExtension,
//                     @Param("fileSize") double fileSize,
//                     @Param("link") Long link,
//                     @Param("uploadedDate") String uploadedDate,
//                     @Param("id") Long id);

}