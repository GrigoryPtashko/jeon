package geek.ma1uta.jeon.server.service

import geek.ma1uta.jeon.server.Query
import geek.ma1uta.jeon.server.model.User
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import java.sql.ResultSet

@Service
class UserService(val query: Query, val template: NamedParameterJdbcTemplate) {

    fun create(user: User) = change(user, query.user.create)

    fun read(userId: String) = template.queryForObject(query.user.read, mutableMapOf(Pair("id", userId)), UserRowMapper())

    fun update(user: User) = change(user, query.user.update)

    fun findUserByToken(token: String) = template.queryForObject(query.user.findByToken, mutableMapOf(Pair("token", token)), UserService.UserRowMapper())

    protected fun change(user: User, sql: String): User {
        template.execute(sql, mutableMapOf(
                Pair("id", user.id),
                Pair("display_name", user.displayName),
                Pair("avatar_url", user.avatarUrl)
        ), { ps -> ps.execute() })
        return template.queryForObject(sql, mutableMapOf(Pair("id", user.id)), UserRowMapper())
    }

    class UserRowMapper : RowMapper<User> {
        override fun mapRow(rs: ResultSet?, rowNum: Int) = User(
                rs!!.getString("id"),
                rs.getString("display_name"),
                rs.getString("avatar_url")
        )
    }
}