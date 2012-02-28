//
//  Model.h
//  GuiaTv
//
//  Created by Jordi Coscolla on 21/02/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "ChannelModel.h"

@interface Model : NSObject
{
    NSMutableArray *channels;
}
- (Model*) initFromServer;

@property (nonatomic, retain) NSMutableArray * channels;

@end
