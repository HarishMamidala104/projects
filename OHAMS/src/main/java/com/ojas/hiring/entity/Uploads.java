package com.ojas.hiring.entity;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "data_uploads")
public class Uploads {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "upload_id", unique = true, nullable = false)
	private long uploadId;

	@Lob
	@Column(name = "uploaded_file")
	private byte[] uploadedFile;
	private String fileName;
	private String extension;
	private double fileSize;
	private String uploadedDate;
	private String uploadedModule;
	private long link;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cid")
//    @JsonManagedReference
//    private Candidate candidate;

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}

	public long getUploadId() {
		return uploadId;
	}

	public void setUploadId(long uploadId) {
		this.uploadId = uploadId;
	}

	public byte[] getUploadedFile() {
		return uploadedFile;
	}

//	public Candidate getCandidate() {
//		return candidate;
//	}
//
//	public void setCandidate(Candidate candidate) {
//		this.candidate = candidate;
//	}

	public void setUploadedFile(byte[] uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(String uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public String getUploadedModule() {
		return uploadedModule;
	}

	public void setUploadedModule(String uploadedModule) {
		this.uploadedModule = uploadedModule;
	}

	public long getLink() {
		return link;
	}

	public void setLink(long link) {
		this.link = link;
	}

	public Uploads() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Uploads(long uploadId, byte[] uploadedFile, String fileName, String extension, double fileSize,
			String uploadedDate, String uploadedModule, long link) {
		super();
		this.uploadId = uploadId;
		this.uploadedFile = uploadedFile;
		this.fileName = fileName;
		this.extension = extension;
		this.fileSize = fileSize;
		this.uploadedDate = uploadedDate;
		this.uploadedModule = uploadedModule;
		this.link = link;
//		this.candidate = candidate;
	}

	public Uploads(long uploadId, byte[] uploadedFile, String fileName, String extension, double fileSize,
			String uploadedDate, long link) {
		super();
		this.uploadId = uploadId;
		this.uploadedFile = uploadedFile;
		this.fileName = fileName;
		this.extension = extension;
		this.fileSize = fileSize;
		this.uploadedDate = uploadedDate;
		this.link = link;
	}

	@Override
	public String toString() {
		return "Uploads [uploadId=" + uploadId + ", uploadedFile=" + Arrays.toString(uploadedFile) + ", fileName="
				+ fileName + ", extension=" + extension + ", fileSize=" + fileSize + ", uploadedDate=" + uploadedDate
				+ ", uploadedModule=" + uploadedModule + ", link=" + link + "]";
	}

}
