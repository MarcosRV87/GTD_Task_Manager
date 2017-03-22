package com.sdi.persistence.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.sdi.persistence.PersistenceException;
import com.sdi.persistence.Transaction;
import com.sdi.persistence.util.Jdbc;

public class TransactionJdbcImpl implements Transaction {
	
	private Connection con;

	@Override
	public void begin() {
		assertNullConnection();
		con = Jdbc.createConnection();
		try {
			con.setAutoCommit( false );
		} catch (SQLException e) {
			throw new PersistenceException( e );
		}
	}

	@Override
	public void commit() {
		assertNonNullConnection();
		assertOpenConnection();
		try {
			con.commit();
			con.setAutoCommit( true ); // makes Jdbc.close() to really close connection
		} catch (SQLException e) {
			throw new PersistenceException( e );
		}
		finally {
			Jdbc.close( con );
		}
	}

	@Override
	public void rollback() {
		assertNonNullConnection();
		assertOpenConnection();
		try {
			con.rollback();
			con.setAutoCommit( true ); // makes Jdbc.close() to really close connection
		} catch (SQLException e) {
			throw new PersistenceException( e );
		}
		finally {
			Jdbc.close( con );
		}
	}

	private void assertNullConnection() {
		if (con == null) return;
		throw new PersistenceException("Transaction is already initiated");
	}

	private void assertNonNullConnection() {
		if (con != null) return;
		throw new PersistenceException("Transaction is not initiated. "
				+ "Call begin() method before use it.");
	}

	private void assertOpenConnection() {
		if ( connectionIsOpen() ) return;
		throw new PersistenceException("Transaction is not initiated. "
				+ "Call begin() method before use it.");
	}

	private boolean connectionIsOpen() {
		try {
			return ! con.isClosed();
		} catch (SQLException e) {
			throw new PersistenceException("Unexpected JDBC error");
		}
	}

}
