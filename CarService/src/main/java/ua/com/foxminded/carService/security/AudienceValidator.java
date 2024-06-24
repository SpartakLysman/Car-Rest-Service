package ua.com.foxminded.carService.security;

//public class AudienceValidator implements OAuth2TokenValidator<Jwt> {
//	private final String audience;
//
//	public AudienceValidator(String audience) {
//		this.audience = audience;
//	}
//
//	@Override
//	public OAuth2TokenValidatorResult validate(Jwt jwt) {
//		List<String> audiences = jwt.getAudience();
//		if (audiences.contains(audience)) {
//			return OAuth2TokenValidatorResult.success();
//		} else {
//			OAuth2Error error = new OAuth2Error("invalid_token", "The required audience is missing", null);
//			return OAuth2TokenValidatorResult.failure(error);
//		}
//	}
//}