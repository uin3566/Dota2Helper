package com.fangxu.dota2helper.bean;

/**
 * Created by lenov0 on 2016/4/26.
 */
public class VideoDetailInfo {

    private String id;
    private String title;
    private String link;
    private String thumbnail;
    private int duration;
    private String category;
    private String state;
    private String created;
    private String published;
    private String description;
    private String player;
    private String public_type;
    private String copyright_type;
    private User user;
    private String tags;
    private int view_count;
    private int favorite_count;
    private int comment_count;
    private int up_count;
    private int down_count;

    public static class User{
        private String id;
        private String name;
        private String link;
    }
//    {
//        "id" : "XNDY5Njc0MTA4",
//            "title" : "康熙来了 121101",
//            "link" : "http://v.youku.com/v_show/id_XNDY5Njc0MTA4.html",
//            "thumbnail" : "http://g2.ykimg.com/01270F1F46509251F539D10123193CD2CB70CC-5896-F53E-B869-61E819861E71",
//            "duration" : "2675.36",
//            "category" : "综艺",
//            "state" : "normal",
//            "created" : "2011-07-15 09:00:42",
//            "published" : "2011-07-15 09:00:42",
//            "description" : "康熙来了",
//            "player" : "http://player.youku.com/player.php/sid/XNDY5Njc0MTA4/v.swf",
//            "public_type" : "all",
//            "copyright_type" : "reproduced",
//            "user" :
//        {
//            "id" : 58011986,
//                "name" : "康熙来了2010",
//                "link" : "http://i.youku.com/u/UMjMyMDQ3OTQ0"
//        },
//        "tags": "康熙来了",
//            "view_count" : 646437,
//            "favorite_count": "124",
//            "comment_count": "547",
//            "up_count": 3060,
//            "down_count": 724,
//            "reference_count": 0,
//            "operation_limit": [ ],
//        "streamtypes": [
//        "hd2",
//                "flvhd",
//                "mp4",
//                "3gp",
//                "3gphd"
//        ],
//        "thumbnails": [
//        {
//            "seq": 1,
//                "url": "http://g3.ykimg.com/01270F1F46509251F204590123193C80F22A26-EDE9-96A1-EB60-8FA55743C21C",
//                "is_cover": 0
//        },
//        {
//            "seq": 2,
//                "url": "http://g4.ykimg.com/01270F1F46509251F37D7D0123193C0E845A38-7A41-3CCA-00A9-2908EF58F301",
//                "is_cover": 0
//        },
//        {
//            "seq": 3,
//                "url": "http://g1.ykimg.com/01270F1F46509251F4E7800123193CCF7AB6E4-D027-1157-4F9F-D5783E051474",
//                "is_cover": 0
//        },
//        {
//            "seq": 4,
//                "url": "http://g2.ykimg.com/01270F1F46509251F539D10123193CD2CB70CC-5896-F53E-B869-61E819861E71",
//                "is_cover": 1
//        },
//        {
//            "seq": 5,
//                "url": "http://g3.ykimg.com/01270F1F46509251F625770123193C706B1FB5-4BAB-9077-2701-498C2E1DB452",
//                "is_cover": 0
//        },
//        {
//            "seq": 6,
//                "url": "http://g4.ykimg.com/01270F1F46509251F7DA0F0123193C43216832-6BF1-98C7-A93B-F5F003D06E3A",
//                "is_cover": 0
//        },
//        {
//            "seq": 7,
//                "url": "http://g1.ykimg.com/01270F1F46509251F8DC940123193C8074B788-FA97-3BAE-0273-AAECBCBAC873",
//                "is_cover": 0
//        },
//        {
//            "seq": 8,
//                "url": "http://g2.ykimg.com/01270F1F46509251F966270123193C863C7E46-5504-D446-3BAB-2A056EC1A550",
//                "is_cover": 0
//        }
//        ],
//        "show": {
//        "id": "2ab71ff032cb11e196ac",
//                "name": "康熙来了 2012",
//                "link": "http://www.youku.com/show_page/id_z2ab71ff032cb11e196ac.html",
//                "type": "正片",
//                "seq": "215",
//                "stage": "20121101"
//    },
//        "dvd": {
//        "point": {
//            "story": [
//            {
//                "start": "307043",
//                    "title": "张艾亚大秀台步 蹩脚姿势被嘲笑",
//                    "desc": ""
//            },
//            {
//                "start": "1060907",
//                    "title": "陈汉典野战造型 半裸狂跳骑马舞",
//                    "desc": ""
//            },
//            {
//                "start": "1736034",
//                    "title": "陈汉典大展身手 跳远似飞引欢呼",
//                    "desc": ""
//            },
//            {
//                "start": "2046152",
//                    "title": "小S教瘦身秘诀 超速竞走秀曲线",
//                    "desc": ""
//            }
//            ]
//        },
//        "tv_starttime": "2012-11-01 22:00:00",
//                "desc": "本期《康熙来了》沈玉琳、许建国、王尹平、余筱萍和张艾亚纷纷挑战运动项目，他们每人在球场上表现如何呢？陈汉典为何现场大秀骑马舞？小S竟也放下身段，快速竞走传授她的曲线秘诀。敬请关注！",
//                "person": [
//        {
//            "id": "317206",
//                "name": "余筱萍",
//                "start": "112494",
//                "type": "guest",
//                "link": "http://www.youku.com/star_page/uid_UMTI2ODgyNA=="
//        },
//        {
//            "id": "330092",
//                "name": "沈玉琳",
//                "start": "68347",
//                "type": "guest",
//                "link": "http://www.youku.com/star_page/uid_UMTMyMDM2OA=="
//        },
//        {
//            "id": "419241",
//                "name": "许建国",
//                "start": "490796",
//                "type": "guest",
//                "link": "http://www.youku.com/star_page/uid_UMTY3Njk2NA=="
//        },
//        {
//            "id": "421849",
//                "name": "张艾亚",
//                "start": "313980",
//                "type": "guest",
//                "link": "http://www.youku.com/star_page/uid_UMTY4NzM5Ng=="
//        },
//        {
//            "id": "422446",
//                "name": "王尹平",
//                "start": "245714",
//                "type": "guest",
//                "link": "http://www.youku.com/star_page/uid_UMTY4OTc4NA=="
//        }
//        ]
//    },
//        "source": {
//        "id": 1,
//                "name": "优酷站内WEB上传",
//                "link": "http://www.youku.com/v_up/"
//    }
//    }
}
