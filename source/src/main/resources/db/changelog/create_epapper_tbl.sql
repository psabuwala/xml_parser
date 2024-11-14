CREATE TABLE `epaper` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `news_paper_name` VARCHAR(450) NOT NULL,
  `height` BIGINT(5) NULL,
  `width` BIGINT(5) NULL,
  `dpi` BIGINT(5) NULL,
  `upload_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `file_name` VARCHAR(100) NULL,
  PRIMARY KEY (`id`));
