/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bplow.deep.base.utils.DateUtils;
import com.bplow.deep.stock.domain.SkDaySwrich;
import com.bplow.deep.stock.mapper.SkDaySwrichMapper;
import com.bplow.deep.stock.service.ObserverService;
import com.bplow.deep.stock.service.SwitchDayService;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年3月5日 下午11:35:18
 */
@Service("switchDayService")
public class SwitchDayServiceImpl implements SwitchDayService{

	@Autowired
	SkDaySwrichMapper skDaySwrichMapper;
	
	@Autowired
	ObserverService observerService;
	
	@Override
	public void switchDay() {
		
		SkDaySwrich daySwitch = skDaySwrichMapper.selectByPrimaryKey("1");
		
		String today = daySwitch.getToday();
		
		String currentDay = DateUtils.getShortDay();
		
		if(!currentDay.equals(today)){
			
			daySwitch.setYesterDay(daySwitch.getToday());
			daySwitch.setToday(currentDay);
			
			skDaySwrichMapper.update(daySwitch);
			
			observerService.refresh();
			
		}
		
	}

}
