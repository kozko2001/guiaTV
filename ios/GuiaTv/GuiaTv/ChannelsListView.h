//
//  ChannelsListView.h
//  GuiaTv
//
//  Created by Jordi Coscolla on 23/02/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
@class Model;
@interface ChannelsListView : UIViewController<UITableViewDataSource, UITableViewDelegate>
{
    UITableView *menuTable_;
    Model* _model;
    UIViewController *programacioView;
}
@property (nonatomic, strong) UITableView *menuTable;
@property (nonatomic, strong) Model       *model;
@end
