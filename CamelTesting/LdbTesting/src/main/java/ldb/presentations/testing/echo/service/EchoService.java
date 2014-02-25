package ldb.presentations.testing.echo.service;

public interface EchoService
{
	String echo(String message) throws IllegalArgumentException;
}