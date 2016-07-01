package com.cooksys.ftd.assessment.filesharing.model.db;

import java.sql.Blob;

public class File {
	private Integer fileId;
	private Integer userID;
	private String absolutePath;
	private Blob fileData;
	
	public File(Integer fileId, Integer userID, String absolutePath, Blob fileData) {
		super();
		this.fileId = fileId;
		this.userID = userID;
		this.absolutePath = absolutePath;
		this.fileData = fileData;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public Blob getFileData() {
		return fileData;
	}

	public void setFileData(Blob fileData) {
		this.fileData = fileData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((absolutePath == null) ? 0 : absolutePath.hashCode());
		result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		File other = (File) obj;
		if (absolutePath == null) {
			if (other.absolutePath != null)
				return false;
		} else if (!absolutePath.equals(other.absolutePath))
			return false;
		if (fileId == null) {
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "File [fileId=" + fileId + ", userID=" + userID + ", absolutePath=" + absolutePath + ", fileData="
				+ fileData + "]";
	}
}
