package com.hanpyeon.academyapi.board.service.comment;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.exception.NoSuchMemberException;
import com.hanpyeon.academyapi.board.exception.RequestDeniedException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.service.ImageService;
import com.hanpyeon.academyapi.security.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@WarnLoggable
@Component
public class CommentDeleteManager {
    private final MemberRepository memberRepository;
    private final ImageService imageService;
    public void remove(final Comment comment, final Long requestMemberId) {
        if (!hasAuthority(requestMemberId, comment)) {
            throw new RequestDeniedException("댓글 지울 수 있는 권한 부재",ErrorCode.DENIED_EXCEPTION);
        }
        imageService.removeImage(comment.getImages());
        if (comment.getAdopted()) {
            comment.deAdopt();
        }
    }
    private boolean hasAuthority(final Long requestMemberId, final Comment comment) {
        if (comment.getRegisteredMember().getId().equals(requestMemberId)) {
            return true;
        }
        Member member = memberRepository.findById(requestMemberId)
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER));
        if (member.getRole().equals(Role.MANAGER)) {
            return true;
        }
        return false;
    }
}
