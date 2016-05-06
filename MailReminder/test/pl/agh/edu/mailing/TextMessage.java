package pl.agh.edu.mailing;

import java.util.Objects;

public class TextMessage {
	private String subject;
	private String from;
	private String to;
	private String content;

	public TextMessage(String subject, String from, String to, String content) {
		super();
		this.subject = subject;
		this.from = from;
		this.to = to;
		this.content = content;
	}

	@Override
	public String toString() {
		return subject + " " + from + " " + to + " " + content;
	}

	@Override
	public int hashCode() {
		return Objects.hash(subject, from, to, content);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		TextMessage secondMessage = (TextMessage) obj;
		return Objects.equals(subject, secondMessage.subject) && Objects.equals(from, secondMessage.from)
				&& Objects.equals(to, secondMessage.to) && Objects.equals(content, secondMessage.content);
	}
}
