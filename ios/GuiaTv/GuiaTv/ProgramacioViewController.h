//
//  ProgramacioViewController.h
//  GuiaTv
//
//  Created by Jordi Coscolla on 23/02/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class  ChannelModel;
@class  ProgramacioModel;

@interface ProgramacioViewController : UIViewController<UITableViewDataSource, UITableViewDelegate>
{
    UITableViewCell *tvcell;
    UITableView  *tableView;
    NSDictionary    *dictionarySections;
    UIViewController   *detailView;
}

@property (nonatomic, strong) ChannelModel *channel;
@property (nonatomic, strong) ProgramacioModel      *programas;
@property (nonatomic, assign) IBOutlet UITableViewCell *tvCell;
@property (nonatomic, strong) IBOutlet UITableView     *tableView;
@property (nonatomic, strong) IBOutlet UINavigationBar *navBar;
@end
