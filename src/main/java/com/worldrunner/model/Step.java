package com.worldrunner.model;

import java.io.Serializable;


public class Step implements Serializable {
    private static final long serialVersionUID = 2L;

        private Long id;
        private Long userid;
        private Double distance;
        private java.sql.Timestamp lastupdate;
        private java.sql.Timestamp day;
        private Long steps;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getUserid() {
            return userid;
        }

        public void setUserid(Long userid) {
            this.userid = userid;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public java.sql.Timestamp getLastupdate() {
            return lastupdate;
        }

        public void setLastupdate(java.sql.Timestamp lastupdate) {
            this.lastupdate = lastupdate;
        }

        public java.sql.Timestamp getDay() {
            return day;
        }

        public void setDay(java.sql.Timestamp day) {
            this.day = day;
        }

        public Long getSteps() {
            return steps;
        }

        public void setSteps(Long steps) {
            this.steps = steps;
        }
    }
