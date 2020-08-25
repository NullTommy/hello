package com.MyUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 检查工具类
 * 
 * @author 
 * @date 2016-4-7
 */
public class CheckUtil {

	public static boolean isNotNull(String input) {
		if (null != input && !"".equals(input.trim())) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Long input) {
		if (null != input) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Integer input) {
		if (null != input) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Date input) {
		if (null != input) {
			return true;
		}
		return false;
	}

	// public static boolean isNotNull(Document input)
	// {
	// if( null != input )
	// {
	// return true;
	// }
	// return false;
	// }
	//
	// public static boolean isNotNull(Element input)
	// {
	// if( null != input )
	// {
	// return true;
	// }
	// return false;
	// }
	//
	// public static boolean isNotNull(Node input)
	// {
	// if( null != input )
	// {
	// return true;
	// }
	// return false;
	// }

	public static boolean isNotNull(Object input) {
		if (null != input) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(StringBuffer input) {
		if (null != input && !"".equals(input.toString().trim())) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(List<?> input) {
		if (null != input && input.size() > 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Map<?, ?> input) {
		if (null != input && !input.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Set<?> input) {
		if (null != input && !input.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(String[] input) {
		if (null != input && input.length > 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Object[] input) {
		if (null != input && input.length > 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Class<?> input) {
		if (null != input) {
			return true;
		}
		return false;
	}

	public static boolean isNull(String arg) {
		boolean isNull = true;
		if (arg != null && !"".equals(arg.trim())) {
			isNull = false;
		}
		return isNull;
	}

	public static boolean isNull(int arg) {
		boolean isNull = true;
		if (arg != -1) {
			isNull = false;
		}
		return isNull;
	}

	public static boolean isNull(long arg) {
		boolean isNull = true;
		if (arg != -1) {
			isNull = false;
		}
		return isNull;
	}

	public static boolean isNull(Map<?, ?> arg) {
		boolean isNull = true;
		if (arg != null && !arg.isEmpty()) {
			isNull = false;
		}
		return isNull;
	}

	public static boolean isNull(Object arg) {
		boolean isNull = true;
		if (arg != null) {
			isNull = false;
		}
		return isNull;
	}

	public static boolean isNull(List<?> list) {
		boolean isNull = true;
		if (list != null && list.size() > 0) {
			isNull = false;
		}
		return isNull;
	}
}
