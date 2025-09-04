package asembly.app.dto.chat;

import asembly.app.dto.user.UserResponse;

import java.util.List;

public record ChatWithUsersResponse(String id, String title, List<UserResponse> users){}
