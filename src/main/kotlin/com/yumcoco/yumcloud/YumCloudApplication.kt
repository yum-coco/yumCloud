package com.yumcoco.yumcloud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
//import org.springframework.jdbc.core.JdbcTemplate
//import org.springframework.jdbc.core.query
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.util.*

@SpringBootApplication
class YumCloudApplication

fun main(args: Array<String>) {
	runApplication<YumCloudApplication>(*args)
}

//@RestController
//class MessageController {
//	@GetMapping("/")
//	fun index(@RequestParam("name") name: String) = "Hello, $name!"
//}

//data class Message(val id: String?, val text: String)

//@RestController
//class MessageController {
//	@GetMapping("/")
//	fun index() = listOf(
//		Message("1", "Hello!"),
//		Message("2", "Bonjour!"),
//		Message("3", "Privet!"),
//	)
//}


//@Service
//class MessageService(val db: JdbcTemplate) {
//	fun findMessages(): List<Message> = db.query("select * from messages") { response, _ ->
//		Message(response.getString("id"), response.getString("text"))
//	}
//
//	fun findMessagesById(id: String): List<Message> = db.query("select * from messages where id = ?", id) { response, _ ->
//		Message(response.getString("id"), response.getString("text"))
//	}
//	fun save(message: Message) {
//		val id: String = message.id ?: UUID.randomUUID().toString()
//		db.update(
//			"insert into messages values ( ?, ? )",
//			id, message.text
//		)
//	}
//}

@RestController
class MessageController(val service: MessageService) {
	@GetMapping("/")
	fun index(): List<Message> = service.findMessages()

	@GetMapping("/{id}")
	fun index(@PathVariable id: String): List<Message> =
		service.findMessagesById(id)

	@PostMapping("/")
	fun post(@RequestBody message: Message) {
		service.save(message)
	}
}

@Table("MESSAGES")
data class Message(@Id var id: String?, val text: String)

interface MessageRepository : CrudRepository<Message, String>

@Service
class MessageService(val db: MessageRepository) {
	fun findMessages(): List<Message> = db.findAll().toList()

	fun findMessagesById(id: String): List<Message> = db.findById(id).toList()

	fun save(message: Message) {
		db.save(message)
	}

	fun <T : Any> Optional<out T>.toList(): List<T> =
		if (isPresent) listOf(get()) else emptyList()
}
