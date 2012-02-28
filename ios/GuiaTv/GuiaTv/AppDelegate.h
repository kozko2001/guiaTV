//
//  AppDelegate.h
//  GuiaTv
//
//  Created by Jordi Coscolla on 23/02/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

#import "ChannelsListView.h"
#import "PSStackedViewController.h"
#define XAppDelegate ((AppDelegate *)[[UIApplication sharedApplication] delegate])

@class ViewController;

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) PSStackedViewController *stackController;

@property (strong, nonatomic) UIViewController *channelsList;
@end
