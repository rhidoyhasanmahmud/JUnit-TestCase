package com.rashed.testcaseproject.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rashed.testcaseproject.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class ServiceData {
	public static String USER_IMTIAZ = "imtiaz-123456", USER_RASHED = "rashed-783283",
			USER_AMINUL = "aminul-003205", USER_ABRAR = "ABRAR-123498",
			USER_GUEST = "grp-pmm-user-guest";
	public static String ROLE_ADMIN = "role_super_admin", ROLE_GED = "role_ged_author",
			ROLE_PD = "role_project_director", ROLE_MEMBER = "role_project_member";

	public static List<UserEntity> newUsers() {
		return Arrays.asList(
				new UserEntity(USER_IMTIAZ, "ইমতিয়াজ রাহী"),
				new UserEntity(USER_RASHED, "রাশেদুল ইসলাম"),
				new UserEntity(USER_AMINUL, "আমিনুল হক"),
				new UserEntity(USER_GUEST, "প্রকল্প অতিথি"),
				new UserEntity(USER_ABRAR, "আবরার আহসান"));
	}

	public static Office newOffice(){
		return new Office().setName("ফরিদপুর শহরের টেপাখোলা লেক উন্নয়ন প্রকল্প কার্যালয়")
				.setLayer("Ministry")
				.setMinistry("Ministry");
	}

	public static Project newProject() {
		return new Project()
				.setName("ফরিদপুর শহরের টেপাখোলা লেক উন্নয়ন প্রকল্প")
				.setOfficeId("710c3d47-63a6-469d-90a6-f2c33c81f082")
				.setStartDate(LocalDate.of(2019, 1, 1)).setFinishDate(LocalDate.parse("2022-06-30"))
				.setProjectDirector("ফাহমিদা আক্তার")
				.setProjectDirectorEmpId("8cf5b157-abd3-40db-9ec7-21eadfc977c5")
//				.setFundBySource(projectFundingBySource(projectFinance))
//				.setFundByYear(projectFundingByYears(projectFinance))
				.setFundBySource(projectFundingBySource())
				.setFundByYear(projectFundingByYear())
//				.addProjectArea(projectArea())
				.addProjectAreaList(projectAreas())
				.setFundOverview(projectFundOverview())
				.setAttachments(getFileNames())
				.setActivities("hello");
	}


	public static List<ProjectFinance> projectFundingList() {
		return Arrays.asList(
				new ProjectFinance(1_123_807_000).gob(1_123_807_000).setFinancialYear(2018),
				new ProjectFinance(441_839_000).gob(441_839_000).setFinancialYear(2019),
				new ProjectFinance(86_352_000).gob(86_352_000).setFinancialYear(2020),
				new ProjectFinance().setSource(FundSource.GRANTS).gob(2_000_000),
				new ProjectFinance(2_000_000).setSource(FundSource.GRANTS)
		);
	}

	public static List<ProjectArea> projectAreas() {
		return Arrays.asList(
				new ProjectArea().setArea("Merul Badda").setUnion("Badda").setUpazila("Dhaka North").setDistrict("Dhaka").setDivision("Dhaka"),
				new ProjectArea().setArea("Kajipara").setUnion("Mirpur").setUpazila("Dhaka North").setDistrict("Dhaka").setDivision("Dhaka")
		);
	}

	public static ProjectArea projectArea() {
		return new ProjectArea().setArea("Merul Badda").setUnion("Badda").setUpazila("Dhaka North").setDistrict("Dhaka").setDivision("Dhaka");
	}





	public static ProjectFinance projectFundOverview() {
		ProjectFinance overview = new ProjectFinance(2_106_100_000);
		overview.setGob(BigDecimal.valueOf(2_106_100_000));
		return overview;
	}

	public static List<String> getFileNames(){
		return Arrays.asList("file1","file2","file3");
	}

	public static ProjectFinance getProjectFinance() {
		return new ProjectFinance(2_106_100_000)
		.setGob(BigDecimal.valueOf(2_106_100_000))
		.setSource(FundSource.GRANTS);
	}
	public static Map<FundSource, ProjectFinance> projectFundingBySource() {
		ProjectFinance f1 = new ProjectFinance(2_106_100_000);
		f1.setGob(BigDecimal.valueOf(2_106_100_000));
		f1.setSource(FundSource.GRANTS);

		Map<FundSource, ProjectFinance> data = new HashMap<>();
		data.put(FundSource.GRANTS, f1);
		return data ;
	}
	public static Map<String, ProjectFinance> projectFundingByYear() {
		ProjectFinance f1 = new ProjectFinance(2_106_100_000);
		f1.setGob(BigDecimal.valueOf(2_106_100_000));
		f1.setSource(FundSource.GRANTS);

		Map<String, ProjectFinance> data = new HashMap<>();
		data.put("2020", f1);
		return data ;
	}
	public static Map<FundSource, ProjectFinance> projectFundingBySource(ProjectFinance projectFinance) {

		Map<FundSource, ProjectFinance> data = new HashMap<>();
		data.put(FundSource.GRANTS, projectFinance);
		return data;
	}
	public static Map<String, ProjectFinance> projectFundingByYears(ProjectFinance projectFinance) {
		Map<String, ProjectFinance> data = new HashMap<>();
		data.put("2020", projectFinance);
		return data;
	}

	public static String asJsonString(final Object obj) throws JsonProcessingException {
		return newObjectMapper().writeValueAsString(obj);
	}

	public static <T> T asObject(final String json, Class<T> type) throws JsonMappingException, JsonProcessingException {
		return newObjectMapper().readValue(json, type);
	}

	public static ObjectMapper newObjectMapper() {
		ObjectMapper mapper = new ObjectMapper()
				.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
				.configure(SerializationFeature.INDENT_OUTPUT, true)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.registerModule(new JavaTimeModule())
				.registerModule(new Jdk8Module());
		mapper.addMixIn(Object.class, IgnoreHibernatePropertiesInJackson.class);
		return mapper;
	}
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private abstract class IgnoreHibernatePropertiesInJackson{ }
}
