package fp.bd.frame.sql.dto;

import com.fp.spring.soaApiEngine.annotation.APIField;
import com.fp.spring.soaApiEngine.annotation.APIValueObject;

import java.util.List;

@APIValueObject("关联关系")
public class Join {
    @APIField("主表中文名")
    private String mainTableName;

    @APIField("主表")
    private String mainTable;

    @APIField("关联表中文名")
    private String joinTableName;

    @APIField("关联表")
    private String joinTable;

    @APIField("关联类型")
    private String joinType = "join";

    @APIField("关联关系")
    private List<JoinTable> joinTableList;

    public static class JoinTable {
        @APIField("第一个表名称")
        private String firstTableName;

        @APIField("第一个表")
        private String firstTableCode;

        @APIField("第二个表名称")
        private String secondTableName;

        @APIField("第二个表")
        private String secondTableCode;

        @APIField("第一个字段名称")
        private String firstFieldName;

        @APIField("第一个表")
        private String firstFieldCode;

        @APIField("第二个字段名称")
        private String secondFieldName;

        @APIField("第二个表")
        private String secondFieldCode;

        public String getFirstTableName() {
            return firstTableName;
        }

        public JoinTable setFirstTableName(String firstTableName) {
            this.firstTableName = firstTableName;
            return this;
        }

        public String getFirstTableCode() {
            return firstTableCode;
        }

        public JoinTable setFirstTableCode(String firstTableCode) {
            this.firstTableCode = firstTableCode;
            return this;
        }

        public String getSecondTableName() {
            return secondTableName;
        }

        public JoinTable setSecondTableName(String secondTableName) {
            this.secondTableName = secondTableName;
            return this;
        }

        public String getSecondTableCode() {
            return secondTableCode;
        }

        public JoinTable setSecondTableCode(String secondTableCode) {
            this.secondTableCode = secondTableCode;
            return this;
        }

        public String getFirstFieldName() {
            return firstFieldName;
        }

        public JoinTable setFirstFieldName(String firstFieldName) {
            this.firstFieldName = firstFieldName;
            return this;
        }

        public String getFirstFieldCode() {
            return firstFieldCode;
        }

        public JoinTable setFirstFieldCode(String firstFieldCode) {
            this.firstFieldCode = firstFieldCode;
            return this;
        }

        public String getSecondFieldName() {
            return secondFieldName;
        }

        public JoinTable setSecondFieldName(String secondFieldName) {
            this.secondFieldName = secondFieldName;
            return this;
        }

        public String getSecondFieldCode() {
            return secondFieldCode;
        }

        public JoinTable setSecondFieldCode(String secondFieldCode) {
            this.secondFieldCode = secondFieldCode;
            return this;
        }
    }

    public String getMainTableName() {
        return mainTableName;
    }

    public Join setMainTableName(String mainTableName) {
        this.mainTableName = mainTableName;
        return this;
    }

    public String getMainTable() {
        return mainTable;
    }

    public Join setMainTable(String mainTable) {
        this.mainTable = mainTable;
        return this;
    }

    public String getJoinTableName() {
        return joinTableName;
    }

    public Join setJoinTableName(String joinTableName) {
        this.joinTableName = joinTableName;
        return this;
    }

    public String getJoinTable() {
        return joinTable;
    }

    public Join setJoinTable(String joinTable) {
        this.joinTable = joinTable;
        return this;
    }

    public List<JoinTable> getJoinTableList() {
        return joinTableList;
    }

    public Join setJoinTableList(List<JoinTable> joinTableList) {
        this.joinTableList = joinTableList;
        return this;
    }

    public String getJoinType() {
        return joinType;
    }

    public Join setJoinType(String joinType) {
        this.joinType = joinType;
        return this;
    }
}
