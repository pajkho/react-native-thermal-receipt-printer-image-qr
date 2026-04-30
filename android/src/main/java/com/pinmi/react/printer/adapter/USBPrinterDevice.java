package com.pinmi.react.printer.adapter;

import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

/**
 * Created by xiesubin on 2017/9/21.
 */

public class USBPrinterDevice implements PrinterDevice{
    private UsbDevice mDevice;
    private USBPrinterDeviceId usbPrinterDeviceId;

    public USBPrinterDevice(UsbDevice device) {
        this.usbPrinterDeviceId = USBPrinterDeviceId.valueOf(device.getVendorId(), device.getProductId());
        this.mDevice = device;
    }


    @Override
    public PrinterDeviceId getPrinterDeviceId() {
        return this.usbPrinterDeviceId;
    }

    public UsbDevice getUsbDevice() {
        return this.mDevice;
    }

    @Override
    public WritableMap toRNWritableMap() {
        WritableMap deviceMap = Arguments.createMap();
        deviceMap.putString("device_name", this.mDevice.getDeviceName());
        deviceMap.putInt("device_id", this.mDevice.getDeviceId());
        deviceMap.putInt("vendor_id", this.mDevice.getVendorId());
        deviceMap.putInt("product_id", this.mDevice.getProductId());
        String productName = this.mDevice.getProductName();
        deviceMap.putString("product_name", productName != null ? productName : "");
        deviceMap.putInt("device_class", this.mDevice.getDeviceClass());
        boolean isPrinterClass = this.mDevice.getDeviceClass() == UsbConstants.USB_CLASS_PRINTER;
        WritableArray interfaceClasses = Arguments.createArray();
        for (int i = 0; i < this.mDevice.getInterfaceCount(); i++) {
            int ifaceClass = this.mDevice.getInterface(i).getInterfaceClass();
            interfaceClasses.pushInt(ifaceClass);
            if (ifaceClass == UsbConstants.USB_CLASS_PRINTER) {
                isPrinterClass = true;
            }
        }
        deviceMap.putBoolean("is_printer_class", isPrinterClass);
        deviceMap.putArray("interface_classes", interfaceClasses);
        return deviceMap;
    }

}
