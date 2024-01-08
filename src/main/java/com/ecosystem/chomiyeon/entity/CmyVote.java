//package com.ecosystem.chomiyeon.entity;
//
//import lombok.Getter;
//
//import javax.persistence.*;
//
//@Getter
//@Entity
//@Table(name = "cmy_votes", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {
//                "cmy_poll_id",
//                "cmy_user_id"
//        })
//})
//public class CmyVote extends DateAudit  {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private Long id;
//
//        @ManyToOne(fetch = FetchType.LAZY, optional = false)
//        @JoinColumn(name = "cmy_poll_id", nullable = false)
//        private CmyPoll cmyPoll;
//
//        @ManyToOne(fetch = FetchType.LAZY, optional = false)
//        @JoinColumn(name = "cmy_choice_id", nullable = false)
//        private CmyChoice cmyChoice;
//
//        @ManyToOne(fetch = FetchType.LAZY, optional = false)
//        @JoinColumn(name = "cmy_user_id", nullable = false)
//        private CmyUser cmyUser;
//
//        public void setId(Long id) {
//                this.id = id;
//        }
//
//        public void setCmyPoll(CmyPoll cmyPoll) {
//                this.cmyPoll = cmyPoll;
//        }
//
//        public void setCmyChoice(CmyChoice cmyChoice) {
//                this.cmyChoice = cmyChoice;
//        }
//
//        public void setCmyUser(CmyUser cmyUser) {
//                this.cmyUser = cmyUser;
//        }
//}
