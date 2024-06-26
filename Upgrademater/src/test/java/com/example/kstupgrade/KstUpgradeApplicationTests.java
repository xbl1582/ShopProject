package com.example.kstupgrade;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
class KstUpgradeApplicationTests {
	/**
	 *
	 * @throws ParseException
	 */
	@Test
	void contextLoads() throws ParseException {
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datalist="1706198400000\n" +
				"1706371200000\n" +
				"1709740800000\n" +
				"1707148800000\n" +
				"1706889600000\n" +
				"1706803200000\n" +
				"1706889600000\n" +
				"1708531200000\n" +
				"1707148800000\n" +
				"1708531200000\n" +
				"1709740800000\n" +
				"1708617600000\n" +
				"1709654400000\n" +
				"1709568000000\n" +
				"1707148800000\n" +
				"1709481600000\n" +
				"1709222400000\n" +
				"1709568000000\n" +
				"1709136000000\n" +
				"1709136000000\n" +
				"1708617600000\n" +
				"1709827200000\n" +
				"1709827200000\n" +
				"1709222400000\n" +
				"1709481600000\n" +
				"1709740800000\n" +
				"1709654400000\n" +
				"1709740800000\n" +
				"1709049600000\n" +
				"1707148800000\n" +
				"1710345600000\n" +
				"1710345600000\n" +
				"1710864000000\n" +
				"1711036800000\n" +
				"1711036800000\n" +
				"1711036800000\n" +
				"1711036800000\n" +
				"1711036800000\n" +
				"1711296000000\n" +
				"1711382400000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711468800000\n" +
				"1711555200000\n" +
				"1711555200000\n" +
				"1711555200000\n" +
				"1711555200000\n" +
				"1711555200000\n" +
				"1711555200000\n" +
				"1710432000000\n" +
				"1710691200000\n" +
				"1711695010608\n" +
				"1711702850234\n" +
				"1712028115179\n" +
				"1712028446615\n" +
				"1712037111041\n" +
				"1712116006584\n" +
				"1712117949273\n" +
				"1712474997698\n" +
				"1712475139924\n" +
				"1712549760798\n" +
				"1712555890373\n" +
				"1712556059681\n" +
				"1712561489335\n" +
				"1712561544208\n" +
				"1712569482509\n" +
				"1712569556233\n" +
				"1712647812550\n" +
				"1712720194252\n" +
				"1712720199062\n" +
				"1712720262336\n" +
				"1712724380011\n" +
				"1712744053418\n" +
				"1712810769494";
		String[] datastr=datalist.split("\n");
		for (int i = 0; i <datastr.length ; i++) {
			long time=Long.valueOf(datastr[i]);
			System.out.println(simpleDateFormat.format(time));
		}


	}

}
