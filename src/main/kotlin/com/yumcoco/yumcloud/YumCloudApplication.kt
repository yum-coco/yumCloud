package com.yumcoco.yumcloud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class YumCloudApplication

fun main(args: Array<String>) {
	runApplication<YumCloudApplication>(*args)
}
