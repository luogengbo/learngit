package com.szgentech.metro.vo;

import java.util.List;

import com.szgentech.metro.model.Liattr;

/**
 * jstree模型
 * @author hank
 *
 * 2016年8月24日
 */
public class Jstree {
	private String id;
	private String text;
	private String icon;
	private String type;
	private String url;
	private State state;
	private Liattr li_attr;
	private List<Jstree> children;
	
	/**
	 * 颜色
	 * 权限树已完工的要变颜色
	 */
	public Liattr getLi_attr() {
		return li_attr;
	}

	public void setLi_attr(Liattr li_attr) {
		this.li_attr = li_attr;
	}

	/**
	 * 获取id
	 * @return id id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置id
	 * @param id id 
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取text
	 * @return text text
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置text
	 * @param text text 
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 获取icon
	 * @return icon icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 设置icon
	 * @param icon icon 
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 获取type
	 * @return type type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置type
	 * @param type type 
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取url
	 * @return url url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置url
	 * @param url url 
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取state
	 * @return state state
	 */
	public State getState() {
		return state;
	}

	/**
	 * 设置state
	 * @param state state 
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * 获取children
	 * @return children children
	 */
	public List<Jstree> getChildren() {
		return children;
	}

	/**
	 * 设置children
	 * @param children children 
	 */
	public void setChildren(List<Jstree> children) {
		this.children = children;
	}

	public class State{
		private boolean opened;// is the node open
		private boolean disabled;// is the node disabled
		private boolean selected;// is the node selected
		/**
		 * 获取opened
		 * @return opened opened
		 */
		public boolean isOpened() {
			return opened;
		}
		/**
		 * 设置opened
		 * @param opened opened 
		 */
		public void setOpened(boolean opened) {
			this.opened = opened;
		}
		/**
		 * 获取disabled
		 * @return disabled disabled
		 */
		public boolean isDisabled() {
			return disabled;
		}
		/**
		 * 设置disabled
		 * @param disabled disabled 
		 */
		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}
		/**
		 * 获取selected
		 * @return selected selected
		 */
		public boolean isSelected() {
			return selected;
		}
		/**
		 * 设置selected
		 * @param selected selected 
		 */
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
		
	}
}
