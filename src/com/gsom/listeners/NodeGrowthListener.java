package com.gsom.listeners;

import java.util.Map;

import com.gsom.objects.GNode;

public interface NodeGrowthListener {

	public void nodeGrowthComplete(Map<String,GNode> map);
}
