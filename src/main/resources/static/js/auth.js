/**
 * 떠나봄 회원 인증 관련 공통 모듈 (auth.js)
 */

let emailThrottleTimer;


function initPasswordValidation(pwdInputSelector, pwdCheckSelector, ruleMsgSelector, mismatchMsgSelector) {
    let reg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()~_+=\{\}\[\]| \-])[A-Za-z\d!@#$%^&*()~_+=\{\}\[\]| \-]{8,20}$/;

    $(pwdInputSelector).on("keyup", function() {
        let pwd = $(this).val();
        if (reg.test(pwd)) {
            $(ruleMsgSelector).text("사용 가능한 비밀번호입니다.").css("color", "green");
        } else {
            $(ruleMsgSelector).text("8~20자, 영문·숫자·특수문자(!@#$%^&*()~-_+=[]{}\|)를 각각 1개 이상 포함해야 합니다.").css("color", "red");
        }
        checkPasswordMatch(pwdInputSelector, pwdCheckSelector, mismatchMsgSelector);
    });

    $(pwdCheckSelector).on("keyup", function() {
        checkPasswordMatch(pwdInputSelector, pwdCheckSelector, mismatchMsgSelector);
    });
}

function checkPasswordMatch(pwdInputSelector, pwdCheckSelector, mismatchMsgSelector) {
    let pwd = $(pwdInputSelector).val();
    let check = $(pwdCheckSelector).val();

    if (!check) {
        $(mismatchMsgSelector).text("");
        return;
    }

    if (pwd !== check) {
        $(mismatchMsgSelector).text("비밀번호 불일치").css("color", "red");
    } else {
        $(mismatchMsgSelector).text("비밀번호 일치").css("color", "green");
    }
}



function resetEmailAuthUI(sendBtn, verifyBtn, authArea, authCodeInput, emailMsg) {
    sessionStorage.setItem("emailVerified", "false");
    sessionStorage.removeItem("verifiedEmail");
    
    
    if (emailThrottleTimer) {
        clearTimeout(emailThrottleTimer);
    }
    
    if (authArea) $(authArea).hide();

    $(authCodeInput).val("").prop("disabled", false);
    $(verifyBtn).prop("disabled", false);
    $(sendBtn).prop("disabled", false);

    $(emailMsg).text("").css("color", "");
}


// ==========================================
//이메일 인증 통합 세팅 
// ==========================================
function initEmailVerification({
    sendBtn, verifyBtn, authArea, emailMsg, authCodeInput,
    getEmailFunction,
    successCallback, // 인증 성공 후 페이지별로 다르게 처리할 로직을 담는 콜백 함수
    beforeSendCallback
}) {
    
    // 인증번호 발송
    $(sendBtn).click(function () {
        if (typeof beforeSendCallback === "function") {
            if (!beforeSendCallback()) {
                return; // 뷰단 가입 전 검증(이메일 중복 체크 등) 실패 시 발송 중단
            }
        }
        
        let email = getEmailFunction();
        if(!email || email.trim() === "") {
            $(emailMsg).text("이메일을 입력해 주세요.").css("color", "red");
            return;
        }
        $("#fullEmail").val(email); 

        $(emailMsg).text("인증번호 발송 중...").css("color", "orange");

      
        let sendUrl = window.location.href.includes("find") ? "/member/find-account/send" : "/member/send";

        $.ajax({
            url: sendUrl,
            type: "post",
            data: { email: email },
            success: function (res) {
                if (res === "SUCCESS") {
                    sessionStorage.setItem("emailVerified", "false");
                    sessionStorage.removeItem("verifiedEmail");
                    
                    if(authArea) $(authArea).slideDown(); 
                    $(emailMsg).text("인증번호가 발송되었습니다. 메일함을 확인해 주세요.").css("color", "green");
                    $(sendBtn).prop("disabled", true);

                    // 쓰로틀링 타이머 작동 (30초 제한 시작)
                    emailThrottleTimer = setTimeout(function () {
                        $(sendBtn).prop("disabled", false);
                    }, 30000);
                    
                } else if (res === "NOT_FOUND") {
                    $(emailMsg).text("가입되지 않은 이메일 주소입니다.").css("color", "red");
                } else {
                    $(emailMsg).text("발송 실패").css("color", "red");
                }
            },
            error: function() {
                $(emailMsg).text("서버 통신 에러").css("color", "red");
            }
        });
    });

    // 인증번호 확인
    $(verifyBtn).click(function () {
        $.ajax({
            url: "/member/verify",
            type: "post",
            data: { code: $(authCodeInput).val() },
            success: function(res) {
                if (res === "SUCCESS") {
                    sessionStorage.setItem("emailVerified", "true");
                    sessionStorage.setItem("verifiedEmail", getEmailFunction());
                    $(emailMsg).text("✔ 이메일 인증 완료").css("color", "green");
                    $(authCodeInput).prop("disabled", true);
                    $(verifyBtn).prop("disabled", true);
                    $(sendBtn).prop("disabled", true);
                    
                    // 인증 성공 시 주입받은 각 페이지별 후처리 실행
                    if(typeof successCallback === "function") {
                        successCallback();
                    }
                } else {
                    sessionStorage.setItem("emailVerified", "false");
                    $(emailMsg).text("인증 실패").css("color", "red");
                }
            },
            error: function() {
                $(emailMsg).text("서버 통신 에러").css("color", "red");
            }
        });
    });
    
 
}