package net.projects.student.portal.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.projects.student.portal.models.Member;
import net.projects.student.portal.models.User;

@Getter
@Setter
@AllArgsConstructor
public class UserAllDataPayload {
	private User user;
	private Member member;
}
