package com.fangda.maintain.web.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Contract implements Serializable {

    private Long id;

    private Long projectId;

    private String contractName;

    private String contractNo;

    private String legal;

    private String address;

    private String contactPerson;

    private String contractTel;

    private String email;

    private Date dateStart;

    private Date dateEnd;

    private Date dateSignup;

    private String documentId;

    private BigDecimal price;

    private String payDesc;

    private String status;

    private Date dateUpdated;

    private String updatedBy;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.ID
     *
     * @param id the value for fd_contract.ID
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.PROJECT_ID
     *
     * @return the value of fd_contract.PROJECT_ID
     *
     * @mbggenerated
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.PROJECT_ID
     *
     * @param projectId the value for fd_contract.PROJECT_ID
     *
     * @mbggenerated
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.CONTRACT_NAME
     *
     * @return the value of fd_contract.CONTRACT_NAME
     *
     * @mbggenerated
     */
    public String getContractName() {
        return contractName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.CONTRACT_NAME
     *
     * @param contractName the value for fd_contract.CONTRACT_NAME
     *
     * @mbggenerated
     */
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.CONTRACT_NO
     *
     * @return the value of fd_contract.CONTRACT_NO
     *
     * @mbggenerated
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.CONTRACT_NO
     *
     * @param contractNo the value for fd_contract.CONTRACT_NO
     *
     * @mbggenerated
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.LEGAL
     *
     * @return the value of fd_contract.LEGAL
     *
     * @mbggenerated
     */
    public String getLegal() {
        return legal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.LEGAL
     *
     * @param legal the value for fd_contract.LEGAL
     *
     * @mbggenerated
     */
    public void setLegal(String legal) {
        this.legal = legal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.ADDRESS
     *
     * @return the value of fd_contract.ADDRESS
     *
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.ADDRESS
     *
     * @param address the value for fd_contract.ADDRESS
     *
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.CONTACT_PERSON
     *
     * @return the value of fd_contract.CONTACT_PERSON
     *
     * @mbggenerated
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.CONTACT_PERSON
     *
     * @param contactPerson the value for fd_contract.CONTACT_PERSON
     *
     * @mbggenerated
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.CONTRACT_TEL
     *
     * @return the value of fd_contract.CONTRACT_TEL
     *
     * @mbggenerated
     */
    public String getContractTel() {
        return contractTel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.CONTRACT_TEL
     *
     * @param contractTel the value for fd_contract.CONTRACT_TEL
     *
     * @mbggenerated
     */
    public void setContractTel(String contractTel) {
        this.contractTel = contractTel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.EMAIL
     *
     * @return the value of fd_contract.EMAIL
     *
     * @mbggenerated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.EMAIL
     *
     * @param email the value for fd_contract.EMAIL
     *
     * @mbggenerated
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.DATE_START
     *
     * @return the value of fd_contract.DATE_START
     *
     * @mbggenerated
     */
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.DATE_START
     *
     * @param dateStart the value for fd_contract.DATE_START
     *
     * @mbggenerated
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.DATE_END
     *
     * @return the value of fd_contract.DATE_END
     *
     * @mbggenerated
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.DATE_END
     *
     * @param dateEnd the value for fd_contract.DATE_END
     *
     * @mbggenerated
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.DATE_SIGNUP
     *
     * @return the value of fd_contract.DATE_SIGNUP
     *
     * @mbggenerated
     */
    public Date getDateSignup() {
        return dateSignup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.DATE_SIGNUP
     *
     * @param dateSignup the value for fd_contract.DATE_SIGNUP
     *
     * @mbggenerated
     */
    public void setDateSignup(Date dateSignup) {
        this.dateSignup = dateSignup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.DOCUMENT_ID
     *
     * @return the value of fd_contract.DOCUMENT_ID
     *
     * @mbggenerated
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.DOCUMENT_ID
     *
     * @param documentId the value for fd_contract.DOCUMENT_ID
     *
     * @mbggenerated
     */
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.PRICE
     *
     * @return the value of fd_contract.PRICE
     *
     * @mbggenerated
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.PRICE
     *
     * @param price the value for fd_contract.PRICE
     *
     * @mbggenerated
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.PAY_DESC
     *
     * @return the value of fd_contract.PAY_DESC
     *
     * @mbggenerated
     */
    public String getPayDesc() {
        return payDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.PAY_DESC
     *
     * @param payDesc the value for fd_contract.PAY_DESC
     *
     * @mbggenerated
     */
    public void setPayDesc(String payDesc) {
        this.payDesc = payDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.STATUS
     *
     * @return the value of fd_contract.STATUS
     *
     * @mbggenerated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.STATUS
     *
     * @param status the value for fd_contract.STATUS
     *
     * @mbggenerated
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.DATE_UPDATED
     *
     * @return the value of fd_contract.DATE_UPDATED
     *
     * @mbggenerated
     */
    public Date getDateUpdated() {
        return dateUpdated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.DATE_UPDATED
     *
     * @param dateUpdated the value for fd_contract.DATE_UPDATED
     *
     * @mbggenerated
     */
    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.UPDATED_BY
     *
     * @return the value of fd_contract.UPDATED_BY
     *
     * @mbggenerated
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.UPDATED_BY
     *
     * @param updatedBy the value for fd_contract.UPDATED_BY
     *
     * @mbggenerated
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column fd_contract.IS_DELETED
     *
     * @return the value of fd_contract.IS_DELETED
     *
     * @mbggenerated
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column fd_contract.IS_DELETED
     *
     * @param isDeleted the value for fd_contract.IS_DELETED
     *
     * @mbggenerated
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fd_contract
     *
     * @mbggenerated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Contract other = (Contract) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getContractName() == null ? other.getContractName() == null : this.getContractName().equals(other.getContractName()))
            && (this.getContractNo() == null ? other.getContractNo() == null : this.getContractNo().equals(other.getContractNo()))
            && (this.getLegal() == null ? other.getLegal() == null : this.getLegal().equals(other.getLegal()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getContactPerson() == null ? other.getContactPerson() == null : this.getContactPerson().equals(other.getContactPerson()))
            && (this.getContractTel() == null ? other.getContractTel() == null : this.getContractTel().equals(other.getContractTel()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getDateStart() == null ? other.getDateStart() == null : this.getDateStart().equals(other.getDateStart()))
            && (this.getDateEnd() == null ? other.getDateEnd() == null : this.getDateEnd().equals(other.getDateEnd()))
            && (this.getDateSignup() == null ? other.getDateSignup() == null : this.getDateSignup().equals(other.getDateSignup()))
            && (this.getDocumentId() == null ? other.getDocumentId() == null : this.getDocumentId().equals(other.getDocumentId()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getPayDesc() == null ? other.getPayDesc() == null : this.getPayDesc().equals(other.getPayDesc()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDateUpdated() == null ? other.getDateUpdated() == null : this.getDateUpdated().equals(other.getDateUpdated()))
            && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fd_contract
     *
     * @mbggenerated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getContractName() == null) ? 0 : getContractName().hashCode());
        result = prime * result + ((getContractNo() == null) ? 0 : getContractNo().hashCode());
        result = prime * result + ((getLegal() == null) ? 0 : getLegal().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getContactPerson() == null) ? 0 : getContactPerson().hashCode());
        result = prime * result + ((getContractTel() == null) ? 0 : getContractTel().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getDateStart() == null) ? 0 : getDateStart().hashCode());
        result = prime * result + ((getDateEnd() == null) ? 0 : getDateEnd().hashCode());
        result = prime * result + ((getDateSignup() == null) ? 0 : getDateSignup().hashCode());
        result = prime * result + ((getDocumentId() == null) ? 0 : getDocumentId().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getPayDesc() == null) ? 0 : getPayDesc().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDateUpdated() == null) ? 0 : getDateUpdated().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }
}