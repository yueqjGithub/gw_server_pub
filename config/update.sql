USE `Avalon_Cat_Website`;

/*Table structure for table `GM_Banners` */

DROP TABLE IF EXISTS `GM_Banners`;

CREATE TABLE `GM_Banners` (
                              `id` varchar(64) NOT NULL,
                              `site_id` varchar(64) NOT NULL COMMENT '站点id',
                              `img_url` varchar(1000) DEFAULT NULL COMMENT '背景图片地址',
                              `min_height` varchar(64) DEFAULT NULL COMMENT 'banner最小高度',
                              `elements` text COMMENT '元素json信息',
                              `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                              `status` tinyint(1) DEFAULT NULL COMMENT '是否启用',
                              `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;