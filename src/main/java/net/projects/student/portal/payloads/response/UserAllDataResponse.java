package net.projects.student.portal.payloads.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.projects.student.portal.models.Member;
import net.projects.student.portal.models.User;

@Getter
@Setter
@AllArgsConstructor
public class UserAllDataResponse {
	private User user;
	private Member member;
}
