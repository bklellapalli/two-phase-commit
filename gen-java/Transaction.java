/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2015-12-09")
public class Transaction implements org.apache.thrift.TBase<Transaction, Transaction._Fields>, java.io.Serializable, Cloneable, Comparable<Transaction> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Transaction");

  private static final org.apache.thrift.protocol.TField TRANSACTION_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("transactionId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField OPERATION_FIELD_DESC = new org.apache.thrift.protocol.TField("operation", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField FILENAME_FIELD_DESC = new org.apache.thrift.protocol.TField("filename", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("content", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PARTICIPANT_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("participantInfo", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField READY_FIELD_DESC = new org.apache.thrift.protocol.TField("ready", org.apache.thrift.protocol.TType.BOOL, (short)6);
  private static final org.apache.thrift.protocol.TField COMMITTED_FIELD_DESC = new org.apache.thrift.protocol.TField("committed", org.apache.thrift.protocol.TType.BOOL, (short)7);
  private static final org.apache.thrift.protocol.TField ABORTED_FIELD_DESC = new org.apache.thrift.protocol.TField("aborted", org.apache.thrift.protocol.TType.BOOL, (short)8);
  private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.BOOL, (short)9);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TransactionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TransactionTupleSchemeFactory());
  }

  public String transactionId; // optional
  public String operation; // optional
  public String filename; // optional
  public String content; // optional
  public String participantInfo; // optional
  public boolean ready; // optional
  public boolean committed; // optional
  public boolean aborted; // optional
  public boolean success; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TRANSACTION_ID((short)1, "transactionId"),
    OPERATION((short)2, "operation"),
    FILENAME((short)3, "filename"),
    CONTENT((short)4, "content"),
    PARTICIPANT_INFO((short)5, "participantInfo"),
    READY((short)6, "ready"),
    COMMITTED((short)7, "committed"),
    ABORTED((short)8, "aborted"),
    SUCCESS((short)9, "success");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TRANSACTION_ID
          return TRANSACTION_ID;
        case 2: // OPERATION
          return OPERATION;
        case 3: // FILENAME
          return FILENAME;
        case 4: // CONTENT
          return CONTENT;
        case 5: // PARTICIPANT_INFO
          return PARTICIPANT_INFO;
        case 6: // READY
          return READY;
        case 7: // COMMITTED
          return COMMITTED;
        case 8: // ABORTED
          return ABORTED;
        case 9: // SUCCESS
          return SUCCESS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __READY_ISSET_ID = 0;
  private static final int __COMMITTED_ISSET_ID = 1;
  private static final int __ABORTED_ISSET_ID = 2;
  private static final int __SUCCESS_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.TRANSACTION_ID,_Fields.OPERATION,_Fields.FILENAME,_Fields.CONTENT,_Fields.PARTICIPANT_INFO,_Fields.READY,_Fields.COMMITTED,_Fields.ABORTED,_Fields.SUCCESS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TRANSACTION_ID, new org.apache.thrift.meta_data.FieldMetaData("transactionId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OPERATION, new org.apache.thrift.meta_data.FieldMetaData("operation", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FILENAME, new org.apache.thrift.meta_data.FieldMetaData("filename", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CONTENT, new org.apache.thrift.meta_data.FieldMetaData("content", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARTICIPANT_INFO, new org.apache.thrift.meta_data.FieldMetaData("participantInfo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.READY, new org.apache.thrift.meta_data.FieldMetaData("ready", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.COMMITTED, new org.apache.thrift.meta_data.FieldMetaData("committed", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.ABORTED, new org.apache.thrift.meta_data.FieldMetaData("aborted", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Transaction.class, metaDataMap);
  }

  public Transaction() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Transaction(Transaction other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetTransactionId()) {
      this.transactionId = other.transactionId;
    }
    if (other.isSetOperation()) {
      this.operation = other.operation;
    }
    if (other.isSetFilename()) {
      this.filename = other.filename;
    }
    if (other.isSetContent()) {
      this.content = other.content;
    }
    if (other.isSetParticipantInfo()) {
      this.participantInfo = other.participantInfo;
    }
    this.ready = other.ready;
    this.committed = other.committed;
    this.aborted = other.aborted;
    this.success = other.success;
  }

  public Transaction deepCopy() {
    return new Transaction(this);
  }

  @Override
  public void clear() {
    this.transactionId = null;
    this.operation = null;
    this.filename = null;
    this.content = null;
    this.participantInfo = null;
    setReadyIsSet(false);
    this.ready = false;
    setCommittedIsSet(false);
    this.committed = false;
    setAbortedIsSet(false);
    this.aborted = false;
    setSuccessIsSet(false);
    this.success = false;
  }

  public String getTransactionId() {
    return this.transactionId;
  }

  public Transaction setTransactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  public void unsetTransactionId() {
    this.transactionId = null;
  }

  /** Returns true if field transactionId is set (has been assigned a value) and false otherwise */
  public boolean isSetTransactionId() {
    return this.transactionId != null;
  }

  public void setTransactionIdIsSet(boolean value) {
    if (!value) {
      this.transactionId = null;
    }
  }

  public String getOperation() {
    return this.operation;
  }

  public Transaction setOperation(String operation) {
    this.operation = operation;
    return this;
  }

  public void unsetOperation() {
    this.operation = null;
  }

  /** Returns true if field operation is set (has been assigned a value) and false otherwise */
  public boolean isSetOperation() {
    return this.operation != null;
  }

  public void setOperationIsSet(boolean value) {
    if (!value) {
      this.operation = null;
    }
  }

  public String getFilename() {
    return this.filename;
  }

  public Transaction setFilename(String filename) {
    this.filename = filename;
    return this;
  }

  public void unsetFilename() {
    this.filename = null;
  }

  /** Returns true if field filename is set (has been assigned a value) and false otherwise */
  public boolean isSetFilename() {
    return this.filename != null;
  }

  public void setFilenameIsSet(boolean value) {
    if (!value) {
      this.filename = null;
    }
  }

  public String getContent() {
    return this.content;
  }

  public Transaction setContent(String content) {
    this.content = content;
    return this;
  }

  public void unsetContent() {
    this.content = null;
  }

  /** Returns true if field content is set (has been assigned a value) and false otherwise */
  public boolean isSetContent() {
    return this.content != null;
  }

  public void setContentIsSet(boolean value) {
    if (!value) {
      this.content = null;
    }
  }

  public String getParticipantInfo() {
    return this.participantInfo;
  }

  public Transaction setParticipantInfo(String participantInfo) {
    this.participantInfo = participantInfo;
    return this;
  }

  public void unsetParticipantInfo() {
    this.participantInfo = null;
  }

  /** Returns true if field participantInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetParticipantInfo() {
    return this.participantInfo != null;
  }

  public void setParticipantInfoIsSet(boolean value) {
    if (!value) {
      this.participantInfo = null;
    }
  }

  public boolean isReady() {
    return this.ready;
  }

  public Transaction setReady(boolean ready) {
    this.ready = ready;
    setReadyIsSet(true);
    return this;
  }

  public void unsetReady() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __READY_ISSET_ID);
  }

  /** Returns true if field ready is set (has been assigned a value) and false otherwise */
  public boolean isSetReady() {
    return EncodingUtils.testBit(__isset_bitfield, __READY_ISSET_ID);
  }

  public void setReadyIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __READY_ISSET_ID, value);
  }

  public boolean isCommitted() {
    return this.committed;
  }

  public Transaction setCommitted(boolean committed) {
    this.committed = committed;
    setCommittedIsSet(true);
    return this;
  }

  public void unsetCommitted() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __COMMITTED_ISSET_ID);
  }

  /** Returns true if field committed is set (has been assigned a value) and false otherwise */
  public boolean isSetCommitted() {
    return EncodingUtils.testBit(__isset_bitfield, __COMMITTED_ISSET_ID);
  }

  public void setCommittedIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __COMMITTED_ISSET_ID, value);
  }

  public boolean isAborted() {
    return this.aborted;
  }

  public Transaction setAborted(boolean aborted) {
    this.aborted = aborted;
    setAbortedIsSet(true);
    return this;
  }

  public void unsetAborted() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ABORTED_ISSET_ID);
  }

  /** Returns true if field aborted is set (has been assigned a value) and false otherwise */
  public boolean isSetAborted() {
    return EncodingUtils.testBit(__isset_bitfield, __ABORTED_ISSET_ID);
  }

  public void setAbortedIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ABORTED_ISSET_ID, value);
  }

  public boolean isSuccess() {
    return this.success;
  }

  public Transaction setSuccess(boolean success) {
    this.success = success;
    setSuccessIsSet(true);
    return this;
  }

  public void unsetSuccess() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SUCCESS_ISSET_ID);
  }

  /** Returns true if field success is set (has been assigned a value) and false otherwise */
  public boolean isSetSuccess() {
    return EncodingUtils.testBit(__isset_bitfield, __SUCCESS_ISSET_ID);
  }

  public void setSuccessIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SUCCESS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TRANSACTION_ID:
      if (value == null) {
        unsetTransactionId();
      } else {
        setTransactionId((String)value);
      }
      break;

    case OPERATION:
      if (value == null) {
        unsetOperation();
      } else {
        setOperation((String)value);
      }
      break;

    case FILENAME:
      if (value == null) {
        unsetFilename();
      } else {
        setFilename((String)value);
      }
      break;

    case CONTENT:
      if (value == null) {
        unsetContent();
      } else {
        setContent((String)value);
      }
      break;

    case PARTICIPANT_INFO:
      if (value == null) {
        unsetParticipantInfo();
      } else {
        setParticipantInfo((String)value);
      }
      break;

    case READY:
      if (value == null) {
        unsetReady();
      } else {
        setReady((Boolean)value);
      }
      break;

    case COMMITTED:
      if (value == null) {
        unsetCommitted();
      } else {
        setCommitted((Boolean)value);
      }
      break;

    case ABORTED:
      if (value == null) {
        unsetAborted();
      } else {
        setAborted((Boolean)value);
      }
      break;

    case SUCCESS:
      if (value == null) {
        unsetSuccess();
      } else {
        setSuccess((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TRANSACTION_ID:
      return getTransactionId();

    case OPERATION:
      return getOperation();

    case FILENAME:
      return getFilename();

    case CONTENT:
      return getContent();

    case PARTICIPANT_INFO:
      return getParticipantInfo();

    case READY:
      return isReady();

    case COMMITTED:
      return isCommitted();

    case ABORTED:
      return isAborted();

    case SUCCESS:
      return isSuccess();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TRANSACTION_ID:
      return isSetTransactionId();
    case OPERATION:
      return isSetOperation();
    case FILENAME:
      return isSetFilename();
    case CONTENT:
      return isSetContent();
    case PARTICIPANT_INFO:
      return isSetParticipantInfo();
    case READY:
      return isSetReady();
    case COMMITTED:
      return isSetCommitted();
    case ABORTED:
      return isSetAborted();
    case SUCCESS:
      return isSetSuccess();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Transaction)
      return this.equals((Transaction)that);
    return false;
  }

  public boolean equals(Transaction that) {
    if (that == null)
      return false;

    boolean this_present_transactionId = true && this.isSetTransactionId();
    boolean that_present_transactionId = true && that.isSetTransactionId();
    if (this_present_transactionId || that_present_transactionId) {
      if (!(this_present_transactionId && that_present_transactionId))
        return false;
      if (!this.transactionId.equals(that.transactionId))
        return false;
    }

    boolean this_present_operation = true && this.isSetOperation();
    boolean that_present_operation = true && that.isSetOperation();
    if (this_present_operation || that_present_operation) {
      if (!(this_present_operation && that_present_operation))
        return false;
      if (!this.operation.equals(that.operation))
        return false;
    }

    boolean this_present_filename = true && this.isSetFilename();
    boolean that_present_filename = true && that.isSetFilename();
    if (this_present_filename || that_present_filename) {
      if (!(this_present_filename && that_present_filename))
        return false;
      if (!this.filename.equals(that.filename))
        return false;
    }

    boolean this_present_content = true && this.isSetContent();
    boolean that_present_content = true && that.isSetContent();
    if (this_present_content || that_present_content) {
      if (!(this_present_content && that_present_content))
        return false;
      if (!this.content.equals(that.content))
        return false;
    }

    boolean this_present_participantInfo = true && this.isSetParticipantInfo();
    boolean that_present_participantInfo = true && that.isSetParticipantInfo();
    if (this_present_participantInfo || that_present_participantInfo) {
      if (!(this_present_participantInfo && that_present_participantInfo))
        return false;
      if (!this.participantInfo.equals(that.participantInfo))
        return false;
    }

    boolean this_present_ready = true && this.isSetReady();
    boolean that_present_ready = true && that.isSetReady();
    if (this_present_ready || that_present_ready) {
      if (!(this_present_ready && that_present_ready))
        return false;
      if (this.ready != that.ready)
        return false;
    }

    boolean this_present_committed = true && this.isSetCommitted();
    boolean that_present_committed = true && that.isSetCommitted();
    if (this_present_committed || that_present_committed) {
      if (!(this_present_committed && that_present_committed))
        return false;
      if (this.committed != that.committed)
        return false;
    }

    boolean this_present_aborted = true && this.isSetAborted();
    boolean that_present_aborted = true && that.isSetAborted();
    if (this_present_aborted || that_present_aborted) {
      if (!(this_present_aborted && that_present_aborted))
        return false;
      if (this.aborted != that.aborted)
        return false;
    }

    boolean this_present_success = true && this.isSetSuccess();
    boolean that_present_success = true && that.isSetSuccess();
    if (this_present_success || that_present_success) {
      if (!(this_present_success && that_present_success))
        return false;
      if (this.success != that.success)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_transactionId = true && (isSetTransactionId());
    list.add(present_transactionId);
    if (present_transactionId)
      list.add(transactionId);

    boolean present_operation = true && (isSetOperation());
    list.add(present_operation);
    if (present_operation)
      list.add(operation);

    boolean present_filename = true && (isSetFilename());
    list.add(present_filename);
    if (present_filename)
      list.add(filename);

    boolean present_content = true && (isSetContent());
    list.add(present_content);
    if (present_content)
      list.add(content);

    boolean present_participantInfo = true && (isSetParticipantInfo());
    list.add(present_participantInfo);
    if (present_participantInfo)
      list.add(participantInfo);

    boolean present_ready = true && (isSetReady());
    list.add(present_ready);
    if (present_ready)
      list.add(ready);

    boolean present_committed = true && (isSetCommitted());
    list.add(present_committed);
    if (present_committed)
      list.add(committed);

    boolean present_aborted = true && (isSetAborted());
    list.add(present_aborted);
    if (present_aborted)
      list.add(aborted);

    boolean present_success = true && (isSetSuccess());
    list.add(present_success);
    if (present_success)
      list.add(success);

    return list.hashCode();
  }

  @Override
  public int compareTo(Transaction other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTransactionId()).compareTo(other.isSetTransactionId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTransactionId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.transactionId, other.transactionId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOperation()).compareTo(other.isSetOperation());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOperation()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.operation, other.operation);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFilename()).compareTo(other.isSetFilename());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFilename()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.filename, other.filename);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetContent()).compareTo(other.isSetContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.content, other.content);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetParticipantInfo()).compareTo(other.isSetParticipantInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetParticipantInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.participantInfo, other.participantInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReady()).compareTo(other.isSetReady());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReady()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ready, other.ready);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCommitted()).compareTo(other.isSetCommitted());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCommitted()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.committed, other.committed);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAborted()).compareTo(other.isSetAborted());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAborted()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.aborted, other.aborted);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSuccess()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, other.success);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Transaction(");
    boolean first = true;

    if (isSetTransactionId()) {
      sb.append("transactionId:");
      if (this.transactionId == null) {
        sb.append("null");
      } else {
        sb.append(this.transactionId);
      }
      first = false;
    }
    if (isSetOperation()) {
      if (!first) sb.append(", ");
      sb.append("operation:");
      if (this.operation == null) {
        sb.append("null");
      } else {
        sb.append(this.operation);
      }
      first = false;
    }
    if (isSetFilename()) {
      if (!first) sb.append(", ");
      sb.append("filename:");
      if (this.filename == null) {
        sb.append("null");
      } else {
        sb.append(this.filename);
      }
      first = false;
    }
    if (isSetContent()) {
      if (!first) sb.append(", ");
      sb.append("content:");
      if (this.content == null) {
        sb.append("null");
      } else {
        sb.append(this.content);
      }
      first = false;
    }
    if (isSetParticipantInfo()) {
      if (!first) sb.append(", ");
      sb.append("participantInfo:");
      if (this.participantInfo == null) {
        sb.append("null");
      } else {
        sb.append(this.participantInfo);
      }
      first = false;
    }
    if (isSetReady()) {
      if (!first) sb.append(", ");
      sb.append("ready:");
      sb.append(this.ready);
      first = false;
    }
    if (isSetCommitted()) {
      if (!first) sb.append(", ");
      sb.append("committed:");
      sb.append(this.committed);
      first = false;
    }
    if (isSetAborted()) {
      if (!first) sb.append(", ");
      sb.append("aborted:");
      sb.append(this.aborted);
      first = false;
    }
    if (isSetSuccess()) {
      if (!first) sb.append(", ");
      sb.append("success:");
      sb.append(this.success);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TransactionStandardSchemeFactory implements SchemeFactory {
    public TransactionStandardScheme getScheme() {
      return new TransactionStandardScheme();
    }
  }

  private static class TransactionStandardScheme extends StandardScheme<Transaction> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Transaction struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TRANSACTION_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.transactionId = iprot.readString();
              struct.setTransactionIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // OPERATION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.operation = iprot.readString();
              struct.setOperationIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FILENAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.filename = iprot.readString();
              struct.setFilenameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.content = iprot.readString();
              struct.setContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // PARTICIPANT_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.participantInfo = iprot.readString();
              struct.setParticipantInfoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // READY
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.ready = iprot.readBool();
              struct.setReadyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // COMMITTED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.committed = iprot.readBool();
              struct.setCommittedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // ABORTED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.aborted = iprot.readBool();
              struct.setAbortedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // SUCCESS
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.success = iprot.readBool();
              struct.setSuccessIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Transaction struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.transactionId != null) {
        if (struct.isSetTransactionId()) {
          oprot.writeFieldBegin(TRANSACTION_ID_FIELD_DESC);
          oprot.writeString(struct.transactionId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.operation != null) {
        if (struct.isSetOperation()) {
          oprot.writeFieldBegin(OPERATION_FIELD_DESC);
          oprot.writeString(struct.operation);
          oprot.writeFieldEnd();
        }
      }
      if (struct.filename != null) {
        if (struct.isSetFilename()) {
          oprot.writeFieldBegin(FILENAME_FIELD_DESC);
          oprot.writeString(struct.filename);
          oprot.writeFieldEnd();
        }
      }
      if (struct.content != null) {
        if (struct.isSetContent()) {
          oprot.writeFieldBegin(CONTENT_FIELD_DESC);
          oprot.writeString(struct.content);
          oprot.writeFieldEnd();
        }
      }
      if (struct.participantInfo != null) {
        if (struct.isSetParticipantInfo()) {
          oprot.writeFieldBegin(PARTICIPANT_INFO_FIELD_DESC);
          oprot.writeString(struct.participantInfo);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetReady()) {
        oprot.writeFieldBegin(READY_FIELD_DESC);
        oprot.writeBool(struct.ready);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCommitted()) {
        oprot.writeFieldBegin(COMMITTED_FIELD_DESC);
        oprot.writeBool(struct.committed);
        oprot.writeFieldEnd();
      }
      if (struct.isSetAborted()) {
        oprot.writeFieldBegin(ABORTED_FIELD_DESC);
        oprot.writeBool(struct.aborted);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSuccess()) {
        oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
        oprot.writeBool(struct.success);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TransactionTupleSchemeFactory implements SchemeFactory {
    public TransactionTupleScheme getScheme() {
      return new TransactionTupleScheme();
    }
  }

  private static class TransactionTupleScheme extends TupleScheme<Transaction> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Transaction struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTransactionId()) {
        optionals.set(0);
      }
      if (struct.isSetOperation()) {
        optionals.set(1);
      }
      if (struct.isSetFilename()) {
        optionals.set(2);
      }
      if (struct.isSetContent()) {
        optionals.set(3);
      }
      if (struct.isSetParticipantInfo()) {
        optionals.set(4);
      }
      if (struct.isSetReady()) {
        optionals.set(5);
      }
      if (struct.isSetCommitted()) {
        optionals.set(6);
      }
      if (struct.isSetAborted()) {
        optionals.set(7);
      }
      if (struct.isSetSuccess()) {
        optionals.set(8);
      }
      oprot.writeBitSet(optionals, 9);
      if (struct.isSetTransactionId()) {
        oprot.writeString(struct.transactionId);
      }
      if (struct.isSetOperation()) {
        oprot.writeString(struct.operation);
      }
      if (struct.isSetFilename()) {
        oprot.writeString(struct.filename);
      }
      if (struct.isSetContent()) {
        oprot.writeString(struct.content);
      }
      if (struct.isSetParticipantInfo()) {
        oprot.writeString(struct.participantInfo);
      }
      if (struct.isSetReady()) {
        oprot.writeBool(struct.ready);
      }
      if (struct.isSetCommitted()) {
        oprot.writeBool(struct.committed);
      }
      if (struct.isSetAborted()) {
        oprot.writeBool(struct.aborted);
      }
      if (struct.isSetSuccess()) {
        oprot.writeBool(struct.success);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Transaction struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(9);
      if (incoming.get(0)) {
        struct.transactionId = iprot.readString();
        struct.setTransactionIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.operation = iprot.readString();
        struct.setOperationIsSet(true);
      }
      if (incoming.get(2)) {
        struct.filename = iprot.readString();
        struct.setFilenameIsSet(true);
      }
      if (incoming.get(3)) {
        struct.content = iprot.readString();
        struct.setContentIsSet(true);
      }
      if (incoming.get(4)) {
        struct.participantInfo = iprot.readString();
        struct.setParticipantInfoIsSet(true);
      }
      if (incoming.get(5)) {
        struct.ready = iprot.readBool();
        struct.setReadyIsSet(true);
      }
      if (incoming.get(6)) {
        struct.committed = iprot.readBool();
        struct.setCommittedIsSet(true);
      }
      if (incoming.get(7)) {
        struct.aborted = iprot.readBool();
        struct.setAbortedIsSet(true);
      }
      if (incoming.get(8)) {
        struct.success = iprot.readBool();
        struct.setSuccessIsSet(true);
      }
    }
  }

}

