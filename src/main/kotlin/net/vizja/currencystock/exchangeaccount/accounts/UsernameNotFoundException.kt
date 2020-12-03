package net.vizja.currencystock.exchangeaccount.accounts

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class UsernameNotFoundException(username: String): ResponseStatusException(HttpStatus.NOT_FOUND, "User:$username not found")